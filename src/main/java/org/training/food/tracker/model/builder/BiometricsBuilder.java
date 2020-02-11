package org.training.food.tracker.model.builder;

import org.training.food.tracker.model.Biometrics;
import org.training.food.tracker.model.Lifestyle;
import org.training.food.tracker.model.Sex;
import org.training.food.tracker.model.User;

import java.math.BigDecimal;

public class BiometricsBuilder {
    private Biometrics biometrics;

    private BiometricsBuilder() {
        this.biometrics = new Biometrics();
    }

    public static BiometricsBuilder instance() {
        return new BiometricsBuilder();
    }

    public BiometricsBuilder id(Long id) {
        biometrics.setId(id);
        return this;
    }

    public BiometricsBuilder owner(User owner) {
        biometrics.setOwner(owner);
        return this;
    }

    public BiometricsBuilder age(BigDecimal age) {
        biometrics.setAge(age);
        return this;
    }

    public BiometricsBuilder sex(Sex sex) {
        biometrics.setSex(sex);
        return this;
    }

    public BiometricsBuilder weight(BigDecimal weight) {
        biometrics.setWeight(weight);
        return this;
    }

    public BiometricsBuilder height(BigDecimal height) {
        biometrics.setHeight(height);
        return this;
    }

    public BiometricsBuilder lifestyle(Lifestyle lifestyle) {
        biometrics.setLifestyle(lifestyle);
        return this;
    }

    public Biometrics build() {
        return biometrics;
    }
}
