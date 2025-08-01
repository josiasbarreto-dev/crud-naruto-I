package io.github.josiasbarreto_dev.desafio_naruto.service.strategy;

import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@DisplayName("Testes Abstratos da Estratégia de Criação de Personagens")
public abstract class AbstractCharacterCreationStrategyTest {
    protected static final String DEFAULT_NAME = "Naruto";
    protected static final int DEFAULT_LIFE = 100;

    protected abstract CharacterCreationStrategy getStrategy();
    protected abstract NinjaType getExpectedType();

    @Test
    void shouldCreateNinjaWithCorrectAttributes() {
        Character result = getStrategy().create(DEFAULT_NAME, DEFAULT_LIFE);

        assertNotNull(result);
        assertEquals(DEFAULT_NAME, result.getName());
        assertEquals(DEFAULT_LIFE, result.getLife());
        assertEquals(getExpectedType(), result.getType());
    }

    @Test
    void shouldReturnCorrectNinjaType() {
        assertEquals(getExpectedType(), getStrategy().getType());
    }
}
