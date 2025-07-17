package io.github.josiasbarreto_dev.desafio_naruto.dto;

import io.github.josiasbarreto_dev.desafio_naruto.model.Jutsu;

import java.util.Map;

public record CharacterResponseDTO(
        Long id,
        String name,
        Map<String, Jutsu> jutsus,
        Integer chakra,
        Integer life
){}