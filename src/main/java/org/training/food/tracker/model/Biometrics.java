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
