package io.github.josiasbarreto_dev.desafio_naruto.model;

import io.github.josiasbarreto_dev.desafio_naruto.exception.JutsuNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NinjutsuNinjaTest {
    private NinjutsuNinja ninja;
    private NinjutsuNinja adversary;

    @BeforeEach
    void setUp() {
        ninja = new NinjutsuNinja("Kakashi", 100);
        adversary = new NinjutsuNinja("Zabuza", 100);

        Jutsu chidori = new Jutsu("Chidori", 30, 40); // nome, chakra, dano
        ninja.setJutsus(List.of(chidori));
    }

    @Test
    @DisplayName("Deve usar jutsu com sucesso e adversário levar dano")
    void testUseJutsuSuccess() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(100)).thenReturn(70);

        adversary.setRandom(mockRandom);

        ninja.useJutsu("Chidori", adversary);

        assertEquals(70, adversary.getLife());
    }

    @Test
    @DisplayName("Deve usar jutsu mas adversário esquiva do ataque")
    void testUseJutsuWithDodge() {
        Random mockRandom = mock(Random.class);
        when(mockRandom.nextInt(100)).thenReturn(30);

        adversary.setRandom(mockRandom);

        ninja.useJutsu("Chidori", adversary);

        assertEquals(100, adversary.getLife());
        assertEquals(100, adversary.getChakra());
        assertEquals(60, ninja.getChakra());
        assertEquals(100, ninja.getLife());
    }


    @Test
    @DisplayName("Deve lançar JutsuNotFoundException se jutsu não existir")
    void testJutsuNotFound() {
        Exception exception = assertThrows(JutsuNotFoundException.class, () -> {
            ninja.useJutsu("Rasengan", adversary);
        });

        assertTrue(exception.getMessage().contains("doesn't know the jutsu"));
    }

    @Test
    @DisplayName("Deve retornar o tipo correto")
    void testGetType() {
        assertEquals(NinjaType.NINJUTSU, ninja.getType());
    }

}
