package org.training.food_tracker.controller.command;

import org.itstep.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        // ToDo delete current user (context & session)
        CommandUtility.setUserRole(request, User.ROLE.UNKNOWN, "Guest");
        return "/index.jsp";
    }
}
