package org.training.food_tracker.controller.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        validateCredentialsAndSendBackIfNot(request, response, username, password);

        System.out.println("username: " + username);
        System.out.println("password: " + password);

        response.sendRedirect(request.getContextPath() + "/students");
    }

    private void validateCredentialsAndSendBackIfNot(HttpServletRequest request, HttpServletResponse response,
            String username, String password) throws IOException {
        if( username == null || username.equals("") || password == null || password.equals("")  ){
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
