package io.github.josiasbarreto_dev.desafio_naruto.dto;

public record BattleResponseDTO(
        CharacterResponseDTO attacker,
        CharacterResponseDTO defender
) {
}
