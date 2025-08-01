package io.github.josiasbarreto_dev.desafio_naruto.model;

import io.github.josiasbarreto_dev.desafio_naruto.exception.JutsuNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaijutsuNinjaTest {

    private TaijutsuNinja ninja;
    private TaijutsuNinja adversary;

    @BeforeEach
    void setUp() {
        ninja = new TaijutsuNinja("Rock Lee", 100);
        adversary = new TaijutsuNinja("Kisame", 100);

        Jutsu jutsu = new Jutsu("Konoha Whirlwind", 20, 35);
        ninja.setJutsus(List.of(jutsu));
    }

    @Test
    @DisplayName("Deve usar jutsu com sucesso e reduzir chakra")
    void testUseJutsuSuccess() {
        ninja.useJutsu("Konoha Whirlwind", adversary);

        assertEquals(65, ninja.getChakra());
    }

    @Test
    @DisplayName("Deve lançar JutsuNotFoundException se jutsu não existir")
    void testJutsuNotFound() {
        Exception exception = assertThrows(JutsuNotFoundException.class, () -> {
            ninja.useJutsu("Lotus", adversary);
        });

        assertTrue(exception.getMessage().contains("doesn't know the jutsu"));
    }

    @Test
    @DisplayName("Deve retornar o tipo correto de ninja")
    void testGetType() {
        assertEquals(NinjaType.TAIJUTSU, ninja.getType());
    }
}
