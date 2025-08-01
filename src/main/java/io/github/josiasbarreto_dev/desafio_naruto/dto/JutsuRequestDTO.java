package io.github.josiasbarreto_dev.desafio_naruto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record JutsuRequestDTO(
        @NotBlank(message = "Jutsu name is required.")
        @Size(min = 3, max = 50, message = "Jutsu name must be between 3 and 50 chars.")
        String name,

        @NotNull(message = "Damage is required.")
        @Positive(message = "Damage must be a positive integer.")
        Integer damage,

        @NotNull(message = "Chakra consumption is required.")
        @Positive(message = "Chakra consumption must be a positive integer.")
        Integer chakraConsumption
){}
