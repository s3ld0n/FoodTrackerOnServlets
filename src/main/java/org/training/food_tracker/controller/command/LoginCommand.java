package org.training.food_tracker.controller.command;

import org.training.food_tracker.model.Role;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if( username == null || username.equals("") || password == null || password.equals("")  ){
            //System.out.println("Not");
            return "/login.jsp";
        }
        System.out.println(username + " " + password);
        //System.out.println("Yes!");
        //todo: check login with DB

        if(CommandUtility.checkUserIsLogged(request, username)){
            return "/WEB-INF/error.jsp";
        }

        if (username.equals("Admin")){
            CommandUtility.setUserRole(request, Role.ADMIN, username);
            return "/WEB-INF/admin/adminbasis.jsp";
        } else if(username.equals("User")) {
            CommandUtility.setUserRole(request, Role.USER, username);
            return "/WEB-INF/user/userbasis.jsp";
        } else {
            CommandUtility.setUserRole(request, Role.GUEST, username);
            return "/login.jsp";
        }


    }

}
