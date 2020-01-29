package org.training.food_tracker.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LoginPageCommand implements Command {

    private static final Logger log = LogManager.getLogger(LoginPageCommand.class.getName());

    @Override public String execute(HttpServletRequest request) {
        log.debug("inside LoginPageCommand.execute()");
        return "jsp/login.jsp";
    }
}
