package io.github.josiasbarreto_dev.desafio_naruto.service.strategy;

import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Testes da NinjutsuCreationStrategy")
class NinjutsuCreationStrategyTest extends AbstractCharacterCreationStrategyTest{
    @Override
    protected CharacterCreationStrategy getStrategy() {
        return new NinjutsuCreationStrategy();
    }

    @Override
    protected NinjaType getExpectedType() {
        return NinjaType.NINJUTSU;
    }
}
