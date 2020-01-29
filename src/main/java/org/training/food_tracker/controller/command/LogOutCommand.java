package org.training.food_tracker.controller.command;

import org.training.food_tracker.model.Role;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        // ToDo delete current user (context & session)
        CommandUtility.setUserRole(request, Role.GUEST, "Guest");
        return "/index.jsp";
    }
}
