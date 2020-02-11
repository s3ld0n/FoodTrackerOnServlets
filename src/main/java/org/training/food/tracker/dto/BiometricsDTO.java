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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private BiometricsDTO biometricsDTO;
        
        private Builder builder() {
            return new Builder();
        }

        public Builder age(BigDecimal age) {
            biometricsDTO.setAge(age);
            return this;
        }

        public Builder sex(Sex sex) {
            biometricsDTO.setSex(sex);
            return this;
        }

        public Builder height(BigDecimal height) {
            biometricsDTO.setHeight(height);
            return this;
        }

        public Builder weight(BigDecimal weight) {
            biometricsDTO.setWeight(weight);
            return this;
        }

        public Builder lifestyle(Lifestyle lifestyle) {
            biometricsDTO.setLifestyle(lifestyle);
            return this;
        }
    }
    
    public void setAge(BigDecimal age) {
        this.age = age;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public void setLifestyle(Lifestyle lifestyle) {
        this.lifestyle = lifestyle;
    }

    public BigDecimal getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public Lifestyle getLifestyle() {
        return lifestyle;
    }
}
