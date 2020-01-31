package org.training.food_tracker.controller.command;

import javax.servlet.http.HttpServletRequest;

public class RegistrationPageCommand implements Command {

    @Override public String execute(HttpServletRequest request) {
        return "jsp/registration.jsp";
    }
}
