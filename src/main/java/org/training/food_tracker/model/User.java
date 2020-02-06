package org.training.food_tracker.model;

import java.math.BigDecimal;

public class User  {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String nationalName;
    private Biometrics biometrics;
    private BigDecimal dailyNormCalories;
    private boolean active;
    private Role role;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private User user;

        Builder() {
            this.user = new User();
        }

        public Builder id(Long id) {
            user.setId(id);
            return this;
        }

        public Builder username(String username) {
            user.setUsername(username);
            return this;
        }

        public Builder email(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder password(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder fullName(String fullName) {
            user.setFullName(fullName);
            return this;
        }

        public Builder nationalName(String nationalName) {
            user.setNationalName(nationalName);
            return this;
        }

        public Builder biometrics(Biometrics biometrics) {
            user.setBiometrics(biometrics);
            return this;
        }

        public Builder dailyNormCalories(BigDecimal dailyNormCalories) {
            user.setDailyNormCalories(dailyNormCalories);
            return this;
        }

        public Builder active(boolean active) {
            user.setActive(active);
            return this;
        }

        public Builder role(Role role) {
            user.setRole(role);
            return this;
        }

        public User build() {
            return user;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNationalName() {
        return nationalName;
    }

    public void setNationalName(String nationalName) {
        this.nationalName = nationalName;
    }

    public Biometrics getBiometrics() {
        return this.biometrics;
    }

    public void setBiometrics(Biometrics biometrics) {
        this.biometrics = biometrics;
    }

    public BigDecimal getDailyNormCalories() {
        return dailyNormCalories;
    }

    public void setDailyNormCalories(BigDecimal dailyNormCalories) {
        this.dailyNormCalories = dailyNormCalories;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override public String toString() {
        return "User{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + ", email='"
                       + email + '\'' + ", fullName='" + fullName + '\'' + ", nationalName='" + nationalName + '\''
                       + ", active=" + active + ", role=" + role + '}';
    }
}
