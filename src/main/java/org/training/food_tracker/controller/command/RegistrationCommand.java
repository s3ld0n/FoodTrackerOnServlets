package org.training.food_tracker.controller.command;

import org.training.food_tracker.model.Lifestyle;
import org.training.food_tracker.model.Role;
import org.training.food_tracker.model.Sex;
import org.training.food_tracker.model.User;
import org.training.food_tracker.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class RegistrationCommand implements Command {

    private UserService userService = new UserService();

    @Override public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String nationalName = request.getParameter("nationalName");
        BigDecimal age = new BigDecimal(request.getParameter("age"));
        Sex sex = Sex.valueOf(request.getParameter("sex"));
        BigDecimal weight = new BigDecimal(request.getParameter("weight"));
        BigDecimal height = new BigDecimal(request.getParameter("height"));
        Lifestyle lifestyle = Lifestyle.valueOf(request.getParameter("lifestyle"));
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");

        //TODO password check


        userService.create(User.builder()
                       .username(username)
                       .email(email)
                       .fullName(fullName)
                       .nationalName(nationalName)
                       .active(false)
                       .role(Role.USER)
                       .password(password)
                       .build());

        return "redirect:/login";
    }
}
