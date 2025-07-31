package io.github.josiasbarreto_dev.desafio_naruto.service.strategy;

import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NinjaFactoryContextTest {

    private CharacterCreationStrategy ninjutsuStrategy;
    private CharacterCreationStrategy taijutsuStrategy;
    private NinjaFactoryContext factory;

    @BeforeEach
    void setup() {
        ninjutsuStrategy = mock(CharacterCreationStrategy.class);
        taijutsuStrategy = mock(CharacterCreationStrategy.class);

        when(ninjutsuStrategy.getType()).thenReturn(NinjaType.NINJUTSU);
        when(taijutsuStrategy.getType()).thenReturn(NinjaType.TAIJUTSU);

        factory = new NinjaFactoryContext(List.of(ninjutsuStrategy, taijutsuStrategy));
    }

    @Test
    @DisplayName("Should create a ninja using Ninjutsu strategy")
    void shouldCreateNinjutsuNinja() {
        String name = "Naruto";
        int life = 100;

        Character mockCharacter = mock(Character.class);
        when(mockCharacter.getName()).thenReturn(name);
        when(mockCharacter.getLife()).thenReturn(life);
        when(mockCharacter.getType()).thenReturn(NinjaType.NINJUTSU);

        when(ninjutsuStrategy.create(name, life)).thenReturn(mockCharacter);

        Character result = factory.create(NinjaType.NINJUTSU, name, life);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(life, result.getLife());
        assertEquals(NinjaType.NINJUTSU, result.getType());

        verify(ninjutsuStrategy).create(name, life);
    }

    @Test
    @DisplayName("Should throw exception when no strategy is found for given type")
    void shouldThrowExceptionWhenStrategyNotFound() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> factory.create(NinjaType.INVALID_TYPE, "Itachi", 90)
        );

        assertEquals("No strategy found for ninja type: INVALID_TYPE", exception.getMessage());
    }
}
