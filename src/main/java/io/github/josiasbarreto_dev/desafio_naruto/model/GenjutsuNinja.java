package io.github.josiasbarreto_dev.desafio_naruto.model;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@Entity
@NoArgsConstructor
public class GenjutsuNinja extends Character {
    public GenjutsuNinja(Long id, String name, int age, String village, ArrayList<String> jutsus, int chakra, NinjaType ninjaType) {
        super(id, name, age, village, jutsus, chakra, ninjaType);
    }

    @Override
    public String useJutsu(){
        return "The character " + getName() + " unleashes a Genjutsu attack!";
    }

    @Override
    public String  dodge(){
        return "The attack was dodged using Genjutsu!";
    }
}
