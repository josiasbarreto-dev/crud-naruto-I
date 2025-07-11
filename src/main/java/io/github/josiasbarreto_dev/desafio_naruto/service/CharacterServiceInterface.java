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
    public CharacterResponseDTO createCharacter(CharacterRequestDTO requestDTO);

    public List<CharacterResponseDTO> listCharacter();

    public CharacterResponseDTO getCharacterById(Long characterId);

    public List<CharacterResponseDTO> listCharactersByType(NinjaType ninjaType);

    public void deleteCharacterById(Long characterId);

    @Transactional
    public BattleResponseDTO fight(AttackRequestDTO dto);

    public CharacterResponseDTO addChakra(Long ninjaId, int chakraAmount);
}
