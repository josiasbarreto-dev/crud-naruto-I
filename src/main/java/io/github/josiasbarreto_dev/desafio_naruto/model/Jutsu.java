package io.github.josiasbarreto_dev.desafio_naruto.model;

import jakarta.persistence.*;

@Embeddable
public class Jutsu {
    private int damage;
    private int chakraConsumption;

    public Jutsu() {
    }

    public Jutsu(int damage, int chakraConsumption) {
        this.damage = damage;
        this.chakraConsumption = chakraConsumption;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getChakraConsumption() {
        return chakraConsumption;
    }

    public void setChakraConsumption(int chakraConsumption) {
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


