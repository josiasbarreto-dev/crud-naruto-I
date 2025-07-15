package io.github.josiasbarreto_dev.desafio_naruto.controller;

import io.github.josiasbarreto_dev.desafio_naruto.dto.AttackRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.BattleResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/character")
public interface CharacterControllerInterface {
    @PostMapping
    ResponseEntity<CharacterResponseDTO> createCharacter(@RequestBody CharacterRequestDTO requestDTO);

    @GetMapping
    ResponseEntity<List<CharacterResponseDTO>> listCharacters();

    @GetMapping("/{characterId}")
    ResponseEntity<CharacterResponseDTO> getCharacterById(@PathVariable Long characterId);

    @GetMapping("/type")
    ResponseEntity<List<CharacterResponseDTO>> listCharactersByType(@RequestParam NinjaType ninjaType);

    @DeleteMapping("/{characterId}")
    ResponseEntity<Void> deleteCharacterById(@PathVariable Long characterId);

    @PostMapping("/battle")
    ResponseEntity<BattleResponseDTO> fight(@RequestBody AttackRequestDTO attackRequestDTO);

    @PostMapping("/chakra/{ninjaId}")
    ResponseEntity<CharacterResponseDTO> addChakra(@PathVariable Long ninjaId, @RequestParam int chakraAmount);
}