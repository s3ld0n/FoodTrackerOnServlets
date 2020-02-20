package org.training.food.tracker.dao;

import java.math.BigDecimal;

public interface Const {
    String DATASOURCE_URL = "datasource.url";
    String DATASOURCE_DRIVER_CLASS_NAME = "datasource.driver-class-name";
    String DATASOURCE_USERNAME = "datasource.username";
    String DATASOURCE_PASSWORD = "datasource.password";
    String DATASOURCE_MAX_ACTIVE = "datasource.max-active";
    String DATASOURCE_MAX_IDLE = "datasource.max-idle";
    String DATASOURCE_MIN_IDLE = "datasource.min-idle";
    String DATASOURCE_MAX_WAIT = "datasource.max-wait";
    String DATASOURCE_MAX_OPEN_PREPARED_STATEMENTS = "datasource.max-open-prepared-statements";

    String URL_AUTH_NOT_REQUIRED = "url.auth-not-required";

    String REGEX_USERNAME = "registration-form.regex.username";
    String REGEX_FIRST_OR_LAST_NAME = "^[а-яА-ЯёЁa-zA-ZІіЇїЄє’]+$";
    String REGEX_EMAIL = "registration-form.regex.email";

    String ERROR_MESSAGE = "error.";
    String REGEX_CONFIG = "regex.";

    BigDecimal MIN_AGE = new BigDecimal(18);
    BigDecimal MAX_AGE = new BigDecimal(120);
    BigDecimal MIN_HEIGHT = new BigDecimal(40);
    BigDecimal MAX_HEIGHT = new BigDecimal(340);
    BigDecimal MIN_WEIGHT = new BigDecimal(30);
    BigDecimal MAX_WEIGHT = new BigDecimal(330);




}
