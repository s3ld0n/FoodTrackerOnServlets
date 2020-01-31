package org.training.food_tracker.controller.command;

import org.training.food_tracker.dao.DaoException;
import org.training.food_tracker.model.Role;
import org.training.food_tracker.model.User;
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

        User user;

        try {
            user = userService.findByUsername(username);
        } catch (DaoException e) {
            e.printStackTrace();
            return "jsp/login.jsp";
        }

        if(CommandUtility.checkUserIsLogged(request, username)){
            return "/WEB-INF/error.jsp";
        }

        if (user.getRole() == Role.USER) {
            return "jsp/user/main.jsp";
        } else {
            return "jsp/admin/main.jsp";
        }
    }

}
