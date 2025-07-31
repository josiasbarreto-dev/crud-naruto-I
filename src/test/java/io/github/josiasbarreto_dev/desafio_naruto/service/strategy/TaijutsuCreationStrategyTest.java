package io.github.josiasbarreto_dev.desafio_naruto.service.strategy;

import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Testes da TaijutsuCreationStrategy")
public class TaijutsuCreationStrategyTest extends AbstractCharacterCreationStrategyTest {
    @Override
    protected CharacterCreationStrategy getStrategy() {
        return new TaijutsuCreationStrategy();
    }

    @Override
    protected NinjaType getExpectedType() {
        return NinjaType.TAIJUTSU;
    }
}
