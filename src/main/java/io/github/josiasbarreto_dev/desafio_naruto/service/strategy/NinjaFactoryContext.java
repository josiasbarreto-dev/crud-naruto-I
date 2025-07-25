package io.github.josiasbarreto_dev.desafio_naruto.service.strategy;

import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NinjaFactoryContext {
    private final Map<NinjaType, CharacterCreationStrategy> strategies = new HashMap<>();

    public NinjaFactoryContext(List<CharacterCreationStrategy> strategyList) {
        for (CharacterCreationStrategy strategy : strategyList) {
            strategies.put(strategy.getType(), strategy);
        }
    }

    public Character create(NinjaType type, String name, Integer life) {
        CharacterCreationStrategy strategy = strategies.get(type);
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for ninja type: " + type);
        }
        return strategy.create(name, life);
    }
}


