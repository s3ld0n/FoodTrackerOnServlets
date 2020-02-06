package org.training.food_tracker.service;

import org.training.food_tracker.model.Biometrics;
import org.training.food_tracker.model.Sex;

import java.math.BigDecimal;

public class BiometricsService {

    /**
     * Total energy expenditure calculation using Harris–Benedict equation
     * @return daily norm of calories
     */
    public BigDecimal calculateDailyNorm(Biometrics biometrics) {
        if (biometrics.getSex() == Sex.FEMALE) {
            return (new BigDecimal(655.1)
                            .add(new BigDecimal(9.563).multiply(biometrics.getWeight()))
                            .add(new BigDecimal(1.850).multiply(biometrics.getHeight()))
                            .subtract(new BigDecimal(4.676).multiply(biometrics.getAge())))
                           .multiply(biometrics.getLifestyle().getCoefficient())
                           .setScale(2, BigDecimal.ROUND_HALF_DOWN);
        }
        return (new BigDecimal(66.5)
                        .add(new BigDecimal(13.75).multiply(biometrics.getWeight()))
                        .add(new BigDecimal(5.003).multiply(biometrics.getHeight()))
                        .subtract(new BigDecimal(6.755).multiply(biometrics.getAge())))
                       .multiply(biometrics.getLifestyle().getCoefficient())
                       .setScale(2, BigDecimal.ROUND_HALF_DOWN);
    }
}
