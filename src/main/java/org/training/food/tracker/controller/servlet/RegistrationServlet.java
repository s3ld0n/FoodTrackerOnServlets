package org.training.food.tracker.controller.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.dao.DaoException;
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
        request.getRequestDispatcher("jsp/registration.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        LOG.debug("validate first name {}", RegistrationFormValidator.validateFirstName(firstName));

        Biometrics biometrics = buildBiometrics(request);

        User user = builderUser(request);
        user.setDailyNormCalories(userService.calculateDailyNormCalories(biometrics));
        user = insertUserIntoDB(user);

        biometrics.setOwner(user);
        biometrics = insertBiometricsIntoDB(biometrics);

        user.setBiometrics(biometrics);
    }

    private Biometrics insertBiometricsIntoDB(Biometrics biometrics) {
        try {
            biometrics = biometricsService.create(biometrics);
        } catch (DaoException e) {
            LOG.error("biometrics creation failed", e);
        }
        return biometrics;
    }

    private User insertUserIntoDB(User user) {
        try {
            user = userService.create(user);
        } catch (DaoException e) {
            LOG.error("user creation failed", e);
        }
        return user;
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
