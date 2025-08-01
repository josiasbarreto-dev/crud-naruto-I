package io.github.josiasbarreto_dev.desafio_naruto.dto;

import java.util.List;

public record CharacterResponseDTO(
        Long id,
        String name,
        List<JutsuResponseDTO> jutsus,
        Integer chakra,
        Integer life
){}