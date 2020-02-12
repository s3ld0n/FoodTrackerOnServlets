package org.training.food.tracker.controller.validator;

import java.util.ResourceBundle;

public class RegistrationFormValidator {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");

    public static boolean validateFirstName(String firstName) {
        return firstName.matches(resourceBundle.getString("registration.form.validation.first-name"));
    }
}
