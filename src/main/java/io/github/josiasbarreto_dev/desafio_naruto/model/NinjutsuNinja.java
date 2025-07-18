package io.github.josiasbarreto_dev.desafio_naruto.model;

import io.github.josiasbarreto_dev.desafio_naruto.exception.InsufficientChakraException;
import io.github.josiasbarreto_dev.desafio_naruto.exception.JutsuNotFoundException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.Random;

@Entity
@DiscriminatorValue("NINJUTSU")
@NoArgsConstructor
public class NinjutsuNinja extends Character {
    public NinjutsuNinja(String name, Integer life) {
        super(name, life);
    }

    @Override
    public void useJutsu(String jutsuName, Character adversaryNinja){
        Jutsu jutsu = jutsus.get(jutsuName);
        if(jutsu == null){
            throw new JutsuNotFoundException(name + " doesn't know the jutsu: " + jutsuName);
        }
        if(chakra < jutsu.getChakraConsumption()){
            throw new InsufficientChakraException(name + " doesn't have enough chakra to use " + jutsuName + ". Required: " + jutsu.getChakraConsumption() + ", Available: " + chakra);
        }

        chakra -= jutsu.getChakraConsumption();
        System.out.println(name + " launch the jutsu " + jutsuName);
        adversaryNinja.dodge(jutsu);
    }

    @Override
    public void dodge(Jutsu jutsu) {
        if (new Random().nextInt(100) < 60) {
            System.out.println(name + " used substitution and avoided the damage.");
        } else {
            loseLife(jutsu.getDamage());
            System.out.println(name + " got hit and lost " + jutsu.getDamage() + " of life.");
        }
    }
}