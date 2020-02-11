package org.training.food.tracker.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.training.food.tracker.model.Lifestyle;
import org.training.food.tracker.model.Sex;
import org.training.food.tracker.service.defaults.DayServiceDefault;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ResourceBundle;

public class RegistrationValidationFilter implements Filter {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    private static final Logger LOG = LoggerFactory.getLogger(RegistrationValidationFilter.class.getName());

    @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        LOG.debug("doFilter()");

        String username = request.getParameter("username");
        boolean isValidUsername = username.matches(resourceBundle.getString("registration.form.validation.first-name"));
        LOG.debug("doFilter() :: isValidUsername - {}", isValidUsername);

        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        BigDecimal age = new BigDecimal(request.getParameter("age"));
        Sex sex = Sex.valueOf(request.getParameter("sex"));
        BigDecimal weight = new BigDecimal(request.getParameter("weight"));
        BigDecimal height = new BigDecimal(request.getParameter("height"));
        Lifestyle lifestyle = Lifestyle.valueOf(request.getParameter("lifestyle"));
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
    }
}
