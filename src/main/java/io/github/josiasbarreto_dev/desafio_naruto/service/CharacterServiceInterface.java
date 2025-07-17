package io.github.josiasbarreto_dev.desafio_naruto.service;

import io.github.josiasbarreto_dev.desafio_naruto.dto.AttackRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.BattleResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CharacterServiceInterface {
    @Transactional
    CharacterResponseDTO createCharacter(CharacterRequestDTO requestDTO);

    List<CharacterResponseDTO> listCharacter();

    CharacterResponseDTO getCharacterById(Long characterId);

    List<CharacterResponseDTO> listCharactersByType(NinjaType ninjaType);

    void deleteCharacterById(Long characterId);

    @Transactional
    BattleResponseDTO fight(AttackRequestDTO dto);

    CharacterResponseDTO addChakra(Long ninjaId, Integer chakraAmount);
}
