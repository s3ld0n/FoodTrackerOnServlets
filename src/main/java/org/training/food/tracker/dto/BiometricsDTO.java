package org.training.food.tracker.dto;

import org.training.food.tracker.model.Lifestyle;
import org.training.food.tracker.model.Sex;

import java.math.BigDecimal;

public class BiometricsDTO {

    private BigDecimal age;
    private Sex sex;
    private BigDecimal weight;
    private BigDecimal height;
    private Lifestyle lifestyle;

    public static class Builder {
        private BiometricsDTO biometricsDTO;

        private Builder() {
            this.biometricsDTO = new BiometricsDTO();
        }

        public Builder age(BigDecimal age) {
            biometricsDTO.setAge(age);
            return this;
        }

        public Builder sex(Sex sex) {
            biometricsDTO.setSex(sex);
            return this;
        }

        public Builder weight(BigDecimal weight) {
            biometricsDTO.setWeight(weight);
            return this;
        }

        public Builder height(BigDecimal height) {
            biometricsDTO.setHeight(height);
            return this;
        }

        public Builder lifestyle(Lifestyle lifestyle) {
            biometricsDTO.setLifestyle(lifestyle);
            return this;
        }

        public BiometricsDTO build() {
            return biometricsDTO;
        }
    }

    public BigDecimal getAge() {
        return age;
    }

    public void setAge(BigDecimal age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public Lifestyle getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(Lifestyle lifestyle) {
        this.lifestyle = lifestyle;
    }
}
