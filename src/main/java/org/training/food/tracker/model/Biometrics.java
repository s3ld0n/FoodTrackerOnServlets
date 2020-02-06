package org.training.food.tracker.model;

import java.math.BigDecimal;

public class Biometrics {
    private Long id;
    private User owner;
    private BigDecimal age;
    private Sex sex;
    private BigDecimal weight;
    private BigDecimal height;
    private Lifestyle lifestyle;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Biometrics biometrics;

        Builder() {
            this.biometrics = new Biometrics();
        }

        public Builder id(Long id) {
            biometrics.setId(id);
            return this;
        }

        public Builder owner(User owner) {
            biometrics.setOwner(owner);
            return this;
        }

        public Builder age(BigDecimal age) {
            biometrics.setAge(age);
            return this;
        }

        public Builder sex(Sex sex) {
            biometrics.setSex(sex);
            return this;
        }

        public Builder weight(BigDecimal weight) {
            biometrics.setWeight(weight);
            return this;
        }

        public Builder height(BigDecimal height) {
            biometrics.setHeight(height);
            return this;
        }

        public Builder lifestyle(Lifestyle lifestyle) {
            biometrics.setLifestyle(lifestyle);
            return this;
        }

        public Biometrics build() {
            return biometrics;
        }
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
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
