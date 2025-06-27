package io.github.josiasbarreto_dev.desafio_naruto.dto;

import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;

import java.util.ArrayList;

public record CharacterRequestDTO(
        String name,
        int age,
        String village,
        ArrayList<String> jutsus,
        int chakra,
        NinjaType ninjaType) {}
