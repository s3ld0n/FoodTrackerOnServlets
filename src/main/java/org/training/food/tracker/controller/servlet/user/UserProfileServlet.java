package org.training.food.tracker.controller.servlet.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.controller.UserCredentials;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dto.BiometricsDTO;
import org.training.food.tracker.dto.DTOConverter;
import org.training.food.tracker.dto.UserDTO;
import org.training.food.tracker.model.*;
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

@WebServlet("/user/profile")
public class UserProfileServlet extends HttpServlet {

    private UserService userService;
    private BiometricsService biometricsService;

    private static final Logger LOG = LoggerFactory.getLogger(UserProfileServlet.class.getName());

    @Override public void init() throws ServletException {
        userService = new UserServiceDefault();
        biometricsService = new BiometricsServiceDefault();
    }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("doGet()");
        UserCredentials userCredentials = (UserCredentials) request.getSession().getAttribute("userCredentials");

        LOG.debug("doGet() :: getting user profile page");
        User commonUser;
        Biometrics biometrics;

        try {
            LOG.debug("doGet() :: getting user by username and biometrics by user");
            commonUser = userService.findByUsername(userCredentials.getUsername());
            biometrics = biometricsService.findByOwner(commonUser);
        } catch (DaoException e) {
            LOG.debug("doGet() :: during user find error occurred");
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            return;
        }

        UserDTO userDTO = DTOConverter.userToUserDTO(commonUser);
        request.setAttribute("userDTO", userDTO);

        BiometricsDTO biometricsDTO = DTOConverter.biometricsToBiometricsDTO(biometrics);
        request.setAttribute("biometricsDTO", biometricsDTO);

        request.getRequestDispatcher("/jsp/user/profile.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("doPost() :: updating user's info");

        LOG.debug("doPost() :: building user using request params");
        User newUser = buildUser(request);

        LOG.debug("doPost() :: building biometrics using request params");
        Biometrics biometrics = buildBiometrics(request);

        newUser.setDailyNormCalories(userService.calculateDailyNormCalories(biometrics));

        try {
            LOG.debug("doPost() :: getting old user from DB");
            User oldUser = userService.findByUsername(newUser.getUsername());
            newUser.setId(oldUser.getId());
            biometrics.setOwner(newUser);

            LOG.debug("doPost() :: updating user");
            userService.update(newUser);

            LOG.debug("doPost() :: updating biometrics");
            biometricsService.update(biometrics);

        } catch (DaoException e) {
            LOG.debug("doGet() :: during user update error occurred");
            request.getRequestDispatcher("/jsp/error.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("/user/profile");
    }

    private Biometrics buildBiometrics(HttpServletRequest request) {
        return BiometricsBuilder.instance()
                                        .age(new BigDecimal(Double.parseDouble(request.getParameter("age"))))
                                        .sex(Sex.valueOf(request.getParameter("sex")))
                                        .height(new BigDecimal(Double.parseDouble(request.getParameter("height"))))
                                        .weight(new BigDecimal(Double.parseDouble(request.getParameter("weight"))))
                                        .lifestyle(Lifestyle.valueOf(request.getParameter("lifestyle")))
                                        .build();
    }

    private User buildUser(HttpServletRequest request) {
        return UserBuilder.instance()
                                    .username(request.getParameter("username"))
                                    .firstName(request.getParameter("firstName"))
                                    .lastName(request.getParameter("lastName"))
                                    .email(request.getParameter("email"))
                                    .password(request.getParameter("password"))
                                    .role(Role.valueOf(request.getParameter("role")))
                                    .build();
    }
}
