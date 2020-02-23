package org.training.food.tracker.controller.validator;

public class FoodValidator {

    public static boolean isValidInput(String name, String calories) {
        return isValidCaloriesInput(calories) && isValidNameInput(name);
    }

    private static boolean isValidCaloriesInput(String calories) {
        return calories.matches("\\d{1,3}");
    }

    private static boolean isValidNameInput(String name) {
        return name.length() <= 30;
    }

}
