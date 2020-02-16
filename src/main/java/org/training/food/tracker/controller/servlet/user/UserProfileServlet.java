package org.training.food.tracker.controller.servlet.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.controller.UserCredentials;
import org.training.food.tracker.controller.servlet.admin.AdminFoodListDeleteFoodServlet;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dto.BiometricsDTO;
import org.training.food.tracker.dto.DTOConverter;
import org.training.food.tracker.dto.UserDTO;
import org.training.food.tracker.model.Biometrics;
import org.training.food.tracker.model.User;
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
        UserCredentials userCredentials = (UserCredentials) request.getSession().getAttribute("userCredentials");

        LOG.debug("doGet() :: getting user profile page");
        User commonUser;
        Biometrics biometrics;

        try {
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

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    }
}
