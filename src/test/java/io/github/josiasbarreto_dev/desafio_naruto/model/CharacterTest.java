package io.github.josiasbarreto_dev.desafio_naruto.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest  {
    private Character character;
    private Jutsu jutsuChidori;

    @BeforeEach
    void setUp() {
        character = new CharacterFake("Naruto", 100);
        jutsuChidori = new Jutsu("Chidori", 20, 10);
    }

    @Test
    @DisplayName("Deve adicionar novo jutsu com sucesso")
    void shouldAddJutsuSuccessfully() {
        boolean result = character.addJutsu(jutsuChidori);

        assertTrue(result);
        assertEquals(1, character.getJutsus().size());
        assertEquals(jutsuChidori, character.getJutsus().get(0));
    }

    @Test
    @DisplayName("Não deve adicionar jutsu duplicado")
    void shouldNotAddDuplicateJutsu() {
        character.addJutsu(jutsuChidori);
        boolean result = character.addJutsu(jutsuChidori);

        assertFalse(result);
        assertEquals(1, character.getJutsus().size());
    }

    @Test
    @DisplayName("Deve perder vida com o dano recebido")
    void shouldLoseLifeWithDamage() {
        character.loseLife(30);

        assertEquals(70, character.getLife());
    }

    @Test
    @DisplayName("Não deve perder vida abaixo de zero")
    void shouldNotLoseLifeBelowZero() {
        character.loseLife(150);

        assertEquals(0, character.getLife());
    }

    @Test
    @DisplayName("Deve reduzir vida quando falha")
    void shouldReduceLifeWhenFailed() {
        NinjutsuNinja ninjaQueFalha = new NinjutsuNinja("Kakashi", 100) {
            @Override
            public void dodge(Jutsu jutsu) {
                loseLife(jutsu.getDamage());
            }
        };

        int initialLife = ninjaQueFalha.getLife();
        ninjaQueFalha.dodge(jutsuChidori);

        assertEquals(initialLife - jutsuChidori.getDamage(), ninjaQueFalha.getLife());
    }

    @Test
    @DisplayName("Deve permitir configurar o chakra com o novo valor")
    void shouldSetChakra() {
        int initialChakra = character.getChakra();
        int chakraToAdd = 20;

        character.setChakra(chakraToAdd);

        assertEquals(initialChakra + chakraToAdd, character.getChakra());
    }

}
