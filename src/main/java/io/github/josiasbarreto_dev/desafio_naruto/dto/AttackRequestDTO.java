package io.github.josiasbarreto_dev.desafio_naruto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AttackRequestDTO(
        @NotBlank(message = "Attacker is required.")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters.")
        @Pattern(regexp = "^[A-Z]+(.)*", message = "Name must start with an uppercase letter and can only contain letters and spaces.")
        String attacker,

        @NotBlank(message = "Target is required.")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters.")
        @Pattern(regexp = "^[A-Z]+(.)*", message = "Name must start with an uppercase letter and can only contain letters and spaces.")
        String target,

        @NotBlank(message = "Jutsu is required.")
        @Size(min = 3, max = 50, message = "Jutsu name must be between 3 and 50 characters.")
        @Pattern(regexp = "^[A-Z]+(.)*", message = "Name must start with an uppercase letter and can only contain letters and spaces.")
        String jutsu
){}
