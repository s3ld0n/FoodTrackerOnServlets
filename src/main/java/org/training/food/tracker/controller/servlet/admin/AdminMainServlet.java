package org.training.food.tracker.controller.servlet.admin;

import org.training.food.tracker.controller.UserCredentials;
import org.training.food.tracker.dto.DTOConverter;
import org.training.food.tracker.dto.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/main")
public class AdminMainServlet extends HttpServlet {

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserCredentials userCredentials = (UserCredentials) request.getSession().getAttribute("userCredentials");
        request.setAttribute("loggedUsername", userCredentials.getUsername());

        request.getRequestDispatcher("/WEB-INF/jsp/admin/main.jsp").forward(request, response);
    }
}
