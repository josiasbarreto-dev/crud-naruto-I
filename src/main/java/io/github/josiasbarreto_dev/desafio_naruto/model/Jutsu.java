package io.github.josiasbarreto_dev.desafio_naruto.model;

public class Jutsu {
    private String name;
    private int damage;
    private int chakraConsumption;

    public Jutsu(String name, int damage, int chakraConsumption) {
        this.name = name;
        this.damage = damage;
        this.chakraConsumption = chakraConsumption;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                "name='" + name + '\'' +
                ", damage=" + damage +
                ", chakraConsumption=" + chakraConsumption +
                '}';
    }
}


