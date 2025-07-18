package io.github.josiasbarreto_dev.desafio_naruto.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record JutsuRequestDTO(
        @NotNull(message = "Damage is required.")
        @Positive(message = "Damage must be a positive integer.")
        Integer damage,

        @NotNull(message = "Chakra consumption is required.")
        @Positive(message = "Chakra consumption must be a positive integer.")
        Integer chakraConsumption
){}
