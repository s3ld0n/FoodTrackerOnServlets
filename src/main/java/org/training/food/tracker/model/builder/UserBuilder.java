package org.training.food.tracker.model.builder;

import org.training.food.tracker.model.Biometrics;
import org.training.food.tracker.model.Role;
import org.training.food.tracker.model.User;

import java.math.BigDecimal;

public class UserBuilder {
    private User user;

    private UserBuilder() {
        this.user = new User();
    }

    public static UserBuilder instance() {
        return new UserBuilder();
    }

    public UserBuilder id(Long id) {
        user.setId(id);
        return this;
    }

    public UserBuilder username(String username) {
        user.setUsername(username);
        return this;
    }

    public UserBuilder email(String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder password(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder firstName(String firstName) {
        user.setFirstName(firstName);
        return this;
    }

    public UserBuilder lastName(String lastName) {
        user.setLastName(lastName);
        return this;
    }

    public UserBuilder biometrics(Biometrics biometrics) {
        user.setBiometrics(biometrics);
        return this;
    }

    public UserBuilder dailyNormCalories(BigDecimal dailyNormCalories) {
        user.setDailyNormCalories(dailyNormCalories);
        return this;
    }

    public UserBuilder active(boolean active) {
        user.setActive(active);
        return this;
    }

    public UserBuilder role(Role role) {
        user.setRole(role);
        return this;
    }

    public User build() {
        return user;
    }
}
