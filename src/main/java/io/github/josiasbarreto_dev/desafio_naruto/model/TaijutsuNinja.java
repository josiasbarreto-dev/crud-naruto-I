package io.github.josiasbarreto_dev.desafio_naruto.model;

import io.github.josiasbarreto_dev.desafio_naruto.model.interfaces.Ninja;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@NoArgsConstructor
public class TaijutsuNinja extends Character implements Ninja {
    public TaijutsuNinja(Long id, String name, int age, String village, ArrayList<String> jutsus, int chakra, NinjaType ninjaType) {
        super(id, name, age, village, jutsus, chakra, ninjaType);
    }

    public String useJutsu() {
        return "The character " + getName() + " unleashes a Taijutsu attack!";
    }

    public String dodge() {
        return "The attack was dodged using Taijutsu!";
    }
}