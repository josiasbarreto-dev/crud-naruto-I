package io.github.josiasbarreto_dev.desafio_naruto.service.strategy;

import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjutsuNinja;
import org.springframework.stereotype.Component;

@Component
public class NinjutsuCreationStrategy implements CharacterCreationStrategy{

    @Override
    public Character create(String name, Integer life) {
        return new NinjutsuNinja(name, life);
    }

    @Override
    public NinjaType getType() {
        return NinjaType.NINJUTSU;
    }
}
