package org.training.food.tracker.controller.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.controller.validator.RegistrationFormValidator;
import org.training.food.tracker.dto.UserDTO;
import org.training.food.tracker.model.Lifestyle;
import org.training.food.tracker.model.Sex;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationServlet.class.getName());

    @Override public void init() throws ServletException {

    }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("jsp/registration.jsp").forward(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");

        LOG.debug("validate first name {}", RegistrationFormValidator.validateFirstName(firstName));

        String lastName = request.getParameter("lastName");
        BigDecimal age = new BigDecimal(Double.parseDouble(request.getParameter("age")));
        Sex sex = Sex.valueOf(request.getParameter("sex"));
        BigDecimal weight = new BigDecimal(Double.parseDouble(request.getParameter("weight")));
        BigDecimal height = new BigDecimal(Double.parseDouble(request.getParameter("height")));
        Lifestyle lifestyle = Lifestyle.valueOf(request.getParameter("lifestyle"));
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");

        LOG.debug("obtained attributes : {}, {}, {}, {}, {}", username, email, firstName, lastName, age);
    }
}
