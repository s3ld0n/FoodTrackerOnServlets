package org.training.food_tracker.controller.command;

import org.training.food_tracker.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command{

    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if( username == null || username.equals("") || password == null || password.equals("")  ){
            return "jsp/login.jsp";
        }

        //todo: check login with DB



        if(CommandUtility.checkUserIsLogged(request, username)){
            return "/WEB-INF/error.jsp";
        }

        return null;
    }

}
