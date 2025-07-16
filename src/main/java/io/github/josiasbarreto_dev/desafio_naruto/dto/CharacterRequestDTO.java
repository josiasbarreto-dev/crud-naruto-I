package io.github.josiasbarreto_dev.desafio_naruto.dto;

import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;

import java.util.Map;

public record CharacterRequestDTO(
        String name,
        Map<String, JutsuRequestDTO> jutsus,
        int life,
        NinjaType ninjaType
) {}