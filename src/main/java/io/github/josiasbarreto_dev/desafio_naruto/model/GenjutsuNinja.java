package io.github.josiasbarreto_dev.desafio_naruto.model;

import io.github.josiasbarreto_dev.desafio_naruto.model.interfaces.Ninja;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@Entity
@NoArgsConstructor
public class GenjutsuNinja extends Character implements Ninja {
    public GenjutsuNinja(Long id, String name, int age, String village, ArrayList<String> jutsus, int chakra, NinjaType ninjaType) {
        super(id, name, age, village, jutsus, chakra, ninjaType);
    }

    public String useJutsu(){
        return "The character " + getName() + " unleashes a Genjutsu attack!";
    }

    public String  dodge(){
        return "The attack was dodged using Genjutsu!";
    }
}
