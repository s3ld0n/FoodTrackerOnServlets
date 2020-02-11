package org.training.food.tracker.dto;

import org.training.food.tracker.model.Role;

import java.math.BigDecimal;

public class UserDTO {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private BiometricsDTO biometricsDTO;
    private BigDecimal dailyNorm;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UserDTO userDTO;

        private Builder() {
            this.userDTO = new UserDTO();
        }

        public Builder username(String username) {
            userDTO.setUsername(username);
            return this;
        }

        public Builder password(String password) {
            userDTO.setPassword(password);
            return this;
        }

        public Builder firstName(String firstName) {
            userDTO.setFirstName(firstName);
            return this;
        }

        public Builder lastName(String lastName) {
            userDTO.setLastName(lastName);
            return this;
        }

        public Builder email(String email) {
            userDTO.setEmail(email);
            return this;
        }

        public Builder biometricsDTO(BiometricsDTO biometricsDTO) {
            userDTO.setBiometricsDTO(biometricsDTO);
            return this;
        }

        public Builder role(Role role) {
            userDTO.setRole(role);
            return this;
        }

        public Builder dailyNorm(BigDecimal dailyNorm) {
            userDTO.setDailyNorm(dailyNorm);
            return this;
        }

        public UserDTO build() {
            return userDTO;
        }
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BiometricsDTO getBiometricsDTO() {
        return biometricsDTO;
    }

    public void setBiometricsDTO(BiometricsDTO biometricsDTO) {
        this.biometricsDTO = biometricsDTO;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BigDecimal getDailyNorm() {
        return dailyNorm;
    }

    public void setDailyNorm(BigDecimal dailyNorm) {
        this.dailyNorm = dailyNorm;
    }
}
