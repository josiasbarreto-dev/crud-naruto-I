package io.github.josiasbarreto_dev.desafio_naruto.dto;

import io.github.josiasbarreto_dev.desafio_naruto.model.Jutsu;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;

import java.util.ArrayList;
import java.util.Map;

public record CharacterResponseDTO(
        Long id,
        String name,
        Map<String, Jutsu> jutsus,
        int chakra,
        int life
) {}