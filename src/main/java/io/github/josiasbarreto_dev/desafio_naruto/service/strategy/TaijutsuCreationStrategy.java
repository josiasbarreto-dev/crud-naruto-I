package io.github.josiasbarreto_dev.desafio_naruto.service.strategy;

import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import io.github.josiasbarreto_dev.desafio_naruto.model.TaijutsuNinja;
import org.springframework.stereotype.Component;

@Component
public class TaijutsuCreationStrategy implements CharacterCreationStrategy{
    @Override
    public Character create(String name, Integer life) {
        return new TaijutsuNinja(name, life);
    }

    @Override
    public NinjaType getType() {
        return NinjaType.TAIJUTSU;
    }
}
