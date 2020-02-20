package org.training.food.tracker.controller.util;

import java.util.Locale;
import java.util.ResourceBundle;

public enum ResourceManager {
    INSTANCE;

    private ResourceBundle configBundle;
    private ResourceBundle localeBundle;

    private ResourceManager() {
        configBundle = ResourceBundle.getBundle("config", new Locale("en"));
        localeBundle = ResourceBundle.getBundle("locale", new Locale("en"));
    }

    public String getConfigParameter(String key) {
        return configBundle.getString(key);
    }

    public String getMessage(String key) {
        return localeBundle.getString(key);
    }
}