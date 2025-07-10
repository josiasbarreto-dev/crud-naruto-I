package io.github.josiasbarreto_dev.desafio_naruto.controller;

import io.github.josiasbarreto_dev.desafio_naruto.dto.*;
import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import io.github.josiasbarreto_dev.desafio_naruto.service.CharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/character")
public class CharacterController {
    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @PostMapping
    public ResponseEntity<CharacterResponseDTO> createCharacter(@RequestBody CharacterRequestDTO requestDTO) {
        var character = characterService.createCharacter(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(character);
    }

    @GetMapping
    public ResponseEntity<List<CharacterResponseDTO>> listCharacter() {
        var listCharacter = characterService.listCharacter();
        return ResponseEntity.ok(listCharacter);
    }

    @GetMapping("/{characterId}")
    public ResponseEntity<Character> getCharacterById(@PathVariable Long characterId) {
        var characterInfo = characterService.getCharacterById(characterId);
        return ResponseEntity.ok(characterInfo);
    }

    @GetMapping("/type")
    public ResponseEntity<List<CharacterResponseDTO>> listCharactersByType(@RequestParam NinjaType ninjaType){
        var listOfCharacterTypes = characterService.listCharactersByType(ninjaType);
        return ResponseEntity.ok(listOfCharacterTypes);
    }

    @DeleteMapping("/{characterId}")
    public ResponseEntity<Void> deleteCharacterById(@PathVariable Long characterId){
        characterService.deleteCharacterById(characterId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/battle")
    ResponseEntity<BattleResponseDTO> fight(@RequestBody AttackRequestDTO attackRequestDTO){
        var attackResult = characterService.fight(attackRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(attackResult);
    }

    @PostMapping("/chakra/{ninjaId}")
    ResponseEntity<CharacterResponseDTO> addChakra(@PathVariable Long ninjaId, @RequestParam int chakraAmount){
        var character = characterService.addChakra(ninjaId, chakraAmount);
        return ResponseEntity.status(HttpStatus.CREATED).body(character);
    }
}
