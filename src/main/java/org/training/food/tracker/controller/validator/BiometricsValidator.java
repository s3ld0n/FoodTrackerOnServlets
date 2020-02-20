package org.training.food.tracker.controller.validator;

import org.training.food.tracker.dao.Const;
import org.training.food.tracker.dto.BiometricsDTO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BiometricsValidator {
    private static final String ERROR_MESSAGE = "error.";
    private BiometricsDTO biometricsDTO;

    private Map<String, String> errors;

    public BiometricsValidator() {
        this.biometricsDTO = new BiometricsDTO();
        this.errors = new HashMap<>();
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public boolean containsErrors(HttpServletRequest request) {
        String age = request.getParameter("age");
        String height = request.getParameter("height");
        String weight = request.getParameter("weight");

        if (isValid(age, Const.MIN_AGE, Const.MAX_AGE)) {
            biometricsDTO.setAge(parseBigDecimal(age));
        } else {
            errors.put("age", ERROR_MESSAGE + "age");
        }

        if (isValid(height, Const.MIN_HEIGHT, Const.MAX_HEIGHT)) {
            biometricsDTO.setHeight(parseBigDecimal(height));
        } else {
            errors.put("height", ERROR_MESSAGE + "height");
        }

        if (isValid(weight, Const.MIN_WEIGHT, Const.MAX_WEIGHT)) {
            biometricsDTO.setWeight(parseBigDecimal(weight));
        } else {
            errors.put("weight", ERROR_MESSAGE + "weight");
        }

        return !errors.isEmpty();
    }

    private boolean isValid(String field, BigDecimal min, BigDecimal max) {
        if (!isValidNumeric(field)) {
            return false;
        }

        BigDecimal numericField = parseBigDecimal(field);

        return (numericField.compareTo(min) >= 0) && (numericField.compareTo(max) <= 0);
    }

    private static boolean isValidNumeric(String field) {
        return !field.isEmpty() && isNumeric(field);
    }

    private static boolean isNumeric(String str) {
        return str.matches("\\d+(\\.\\d+)?");
    }

    private BigDecimal parseBigDecimal(String numerical) {
        return new BigDecimal(Double.parseDouble(numerical));
    }

    public BiometricsDTO getBiometricsDTO() {
        return biometricsDTO;
    }
}
