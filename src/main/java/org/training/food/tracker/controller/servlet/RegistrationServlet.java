package org.training.food.tracker.controller.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.controller.validator.BiometricsValidator;
import org.training.food.tracker.controller.validator.UserValidator;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dao.UserAlreadyExistsException;
import org.training.food.tracker.dto.DTOConverter;
import org.training.food.tracker.dto.UserDTO;
import org.training.food.tracker.model.Biometrics;
import org.training.food.tracker.model.Lifestyle;
import org.training.food.tracker.model.Sex;
import org.training.food.tracker.model.User;
import org.training.food.tracker.model.builder.BiometricsBuilder;
import org.training.food.tracker.model.builder.UserBuilder;
import org.training.food.tracker.service.BiometricsService;
import org.training.food.tracker.service.UserService;
import org.training.food.tracker.service.defaults.BiometricsServiceDefault;
import org.training.food.tracker.service.defaults.UserServiceDefault;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private UserService userService;
    private BiometricsService biometricsService;

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationServlet.class.getName());

    @Override public void init() throws ServletException {
        userService = new UserServiceDefault();
        biometricsService = new BiometricsServiceDefault();
    }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("doPost() :: building user from request");
        User user = builderUser(request);

        if (!isValidUserInput(request, user)) {
            request.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(request, response);
            return;
        }

        Biometrics biometrics = buildBiometrics(request);

        LOG.debug("doPost() :: calculating and setting daily norm for user");
        user.setDailyNormCalories(userService.calculateDailyNormCalories(biometrics));

        LOG.debug("doPost() :: inserting user into DB");

        Optional<User> optionalUser = insertUserIntoDB(user);
        if (!optionalUser.isPresent()) {
            request.setAttribute("userExists", true);
            request.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(request, response);
            return;
        }

        biometrics.setOwner(user);
        biometrics = insertBiometricsIntoDB(biometrics);

        user.setBiometrics(biometrics);
    }

    private boolean isValidUserInput(HttpServletRequest request, User user) {
        LOG.debug("isValidUserInput() :: validating user fields");
        UserValidator userValidator = new UserValidator();
        boolean invalidUserFields = userValidator.isNotValid(user);

        LOG.debug("isValidUserInput() :: validating biometrics fields");
        BiometricsValidator biometricsValidator = new BiometricsValidator();
        boolean invalidBiometricFields = biometricsValidator.containsErrors(request);

        LOG.debug("isValidUserInput() :: comparing passwords");
        boolean passwordsDontMatch = userValidator.passwordsDontMatch(
                request.getParameter("password"),
                request.getParameter("passwordConfirm")
        );

        if (passwordsDontMatch) {
            request.setAttribute("passwordsDontMatch", "passwordsDontMatch");
        }

        if (invalidUserFields || invalidBiometricFields) {
            UserDTO userDTO = DTOConverter.userToUserDTO(user);
            request.setAttribute("userDTO", userDTO);
            request.setAttribute("userErrors", userValidator.getErrors());

            request.setAttribute("biometricsErrors", biometricsValidator.getErrors());
            request.setAttribute("biometricsDTO", biometricsValidator.getBiometricsDTO());
        }

        LOG.debug("doPost() :: building biometrics from request");

        if (invalidUserFields || invalidBiometricFields || passwordsDontMatch) {
            return false;
        }
        return true;
    }

    private Biometrics insertBiometricsIntoDB(Biometrics biometrics) {
        try {
            biometrics = biometricsService.create(biometrics);
        } catch (DaoException e) {
            LOG.error("doPost() :: biometrics creation failed", e);
        }
        return biometrics;
    }

    private Optional<User> insertUserIntoDB(User user) {
        try {
            user = userService.create(user);
        } catch (UserAlreadyExistsException e) {
            LOG.debug("user exists, returning Optional empty");
            return Optional.empty();
        } catch (DaoException e) {
            LOG.error("doPost() :: user creation failed", e);
        }
        return Optional.of(user);
    }

    private Biometrics buildBiometrics(HttpServletRequest request) {
        return BiometricsBuilder.instance()
                                    .age(new BigDecimal(Double.parseDouble(request.getParameter("age"))))
                                    .sex(Sex.valueOf(request.getParameter("sex")))
                                    .weight(new BigDecimal(Double.parseDouble(request.getParameter("weight"))))
                                    .height(new BigDecimal(Double.parseDouble(request.getParameter("height"))))
                                    .lifestyle(Lifestyle.valueOf(request.getParameter("lifestyle")))
                                    .build();
    }

    private User builderUser(HttpServletRequest request) {
        return UserBuilder.instance()
                                .username(request.getParameter("username"))
                                .email(request.getParameter("email"))
                                .firstName(request.getParameter("firstName"))
                                .lastName(request.getParameter("lastName"))
                                .password(request.getParameter("password"))
                                .build();
    }
}
