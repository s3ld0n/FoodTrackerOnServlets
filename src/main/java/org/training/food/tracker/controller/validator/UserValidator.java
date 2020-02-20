package org.training.food.tracker.controller.validator;

import org.training.food.tracker.controller.util.ResourceManager;
import org.training.food.tracker.dao.Const;
import org.training.food.tracker.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserValidator {
    private Map<String, String> errors = new HashMap<>();
    private ResourceManager resourceManager = ResourceManager.INSTANCE;

    public Map<String, String> getErrors() {
        return errors;
    }

    private Map<String, String> validate(Map<String, String> input) {
        Map<String, String> errors = new HashMap<>();

        for (Map.Entry<String, String> field : input.entrySet()) {

            if (fieldNotValid(field.getValue(), field.getKey())) {
                errors.put(field.getKey(), Const.ERROR_MESSAGE + field.getKey());
            }
        }

        return errors;
    }

    private boolean fieldNotValid(String value, String regex) {
        return !value.matches(resourceManager.getConfigParameter(Const.REGEX_CONFIG + regex));
    }

    public boolean isNotValid(User user) {
        Map<String, String> input = new HashMap<>();
        input.put("username", user.getUsername());
        input.put("firstName", user.getFirstName());
        input.put("lastName", user.getLastName());
        input.put("email", user.getEmail());
        input.put("password", user.getPassword());
        errors = validate(input);
        return !errors.isEmpty();
    }

    public boolean passwordsDontMatch(String password, String passwordConfirm) {
        return !password.equals(passwordConfirm);
    }
}
