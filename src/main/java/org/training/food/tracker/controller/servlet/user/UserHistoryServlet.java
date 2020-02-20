package org.training.food.tracker.controller.servlet.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.controller.UserCredentials;
import org.training.food.tracker.dao.DaoException;
import org.training.food.tracker.dto.DTOConverter;
import org.training.food.tracker.dto.DayDTO;
import org.training.food.tracker.dto.UserDTO;
import org.training.food.tracker.model.User;
import org.training.food.tracker.service.DayService;
import org.training.food.tracker.service.UserService;
import org.training.food.tracker.service.defaults.DayServiceDefault;
import org.training.food.tracker.service.defaults.UserServiceDefault;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/history")
public class UserHistoryServlet extends HttpServlet {

    private DayService dayService;
    private UserService userService;

    private static final Logger LOG = LoggerFactory.getLogger(UserHistoryServlet.class.getName());

    @Override public void init() throws ServletException {
        dayService = new DayServiceDefault();
        userService = new UserServiceDefault();
    }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("doGet() :: getting userCredentials");
        UserCredentials userCredentials = (UserCredentials) request.getSession().getAttribute("userCredentials");

        User user;
        List<DayDTO> daysDTOs;

        try {
            LOG.debug("doGet() :: finding user by username {}", userCredentials.getUsername());
            user = userService.findByUsername(userCredentials.getUsername());

            LOG.debug("doGet() :: getting days of current user and converting them to DTOs");
            daysDTOs = DTOConverter.daysToDaysDTOs(dayService.getAllDaysByUser(user));
        } catch (DaoException e) {
            LOG.debug("doGet() :: error occurred", e);
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            return;
        }

        LOG.debug("doGet() :: setting daysDTOs");
        request.setAttribute("daysDTOs", daysDTOs);
        UserDTO userDTO = DTOConverter.userToUserDTO(user);

        LOG.debug("doGet() :: setting userDTO");
        request.setAttribute("userDTO", userDTO);
        request.setAttribute("loggedUsername", userCredentials.getUsername());
        request.getRequestDispatcher("/WEB-INF/jsp/user/history.jsp").forward(request, response);
    }
}
