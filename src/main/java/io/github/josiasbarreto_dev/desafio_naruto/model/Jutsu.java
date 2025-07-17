package io.github.josiasbarreto_dev.desafio_naruto.model;

import jakarta.persistence.*;

@Embeddable
public class Jutsu {
    private Integer damage;
    private Integer chakraConsumption;

    public Jutsu() {
    }

    public Jutsu(Integer damage, Integer chakraConsumption) {
        this.damage = damage;
        this.chakraConsumption = chakraConsumption;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getChakraConsumption() {
        return chakraConsumption;
    }

    public void setChakraConsumption(Integer chakraConsumption) {
        this.chakraConsumption = chakraConsumption;
    }

    @Override
    public String toString() {
        return "Jutsu{" +
                ", damage=" + damage +
                ", chakraConsumption=" + chakraConsumption +
                '}';
    }
}


