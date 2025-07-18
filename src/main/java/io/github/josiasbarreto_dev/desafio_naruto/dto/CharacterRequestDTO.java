package io.github.josiasbarreto_dev.desafio_naruto.dto;

import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.Map;

public record CharacterRequestDTO(
        @NotBlank(message = "Name is required.")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters.")
        @Pattern(regexp = "^[A-Z]+(.)*", message = "Name must start with an uppercase letter and can only contain letters and spaces.")
        String name,

        @NotNull(message = "Jutsus cannot be null.")
        @NotEmpty(message = "The jutsu map cannot be empty.")
        @Valid
        Map<String, JutsuRequestDTO> jutsus,

        @NotNull(message = "Life is required.")
        @Positive(message = "Life must be a positive integer.")
        Integer life,

        @NotNull(message = "Ninja is required.")
        NinjaType ninjaType
){}