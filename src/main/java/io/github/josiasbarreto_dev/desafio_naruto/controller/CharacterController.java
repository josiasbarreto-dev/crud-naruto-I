package io.github.josiasbarreto_dev.desafio_naruto.controller;

import io.github.josiasbarreto_dev.desafio_naruto.dto.ChakraRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.JutsuRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import io.github.josiasbarreto_dev.desafio_naruto.service.CharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{characterId}/add-jutsu")
    public ResponseEntity<CharacterResponseDTO> addNewJutsu(@PathVariable("characterId") Long characterId, @RequestBody JutsuRequestDTO dto) {
        var character = characterService.addNewJutsu(characterId, dto);
        return ResponseEntity.ok(character);
    }

    @PatchMapping("/{characterId}")
    public ResponseEntity<CharacterResponseDTO> increaseChakra(@PathVariable("characterId") Long characterId, @RequestBody ChakraRequestDTO chakraDTO){
        return ResponseEntity.ok(characterService.increaseChakra(characterId, chakraDTO));
    }

    @GetMapping("/info/{characterId}")
    public ResponseEntity<String> getDisplayInfo(
            @PathVariable("characterId") Long characterId,
            @RequestParam("ninjaType") NinjaType ninjaType) {
        var characterInfo = characterService.getDisplayInfo(characterId, ninjaType);
        return ResponseEntity.ok(characterInfo);
    }

    @GetMapping("/jutsu/{characterId}")
    public ResponseEntity<String> useJutsuCharacter(
            @PathVariable("characterId") Long characterId,
            @RequestParam("ninjaType") NinjaType ninjaType) {
        var jutsu = characterService.useJutsu(characterId, ninjaType);
        return ResponseEntity.ok(jutsu);
    }
    @GetMapping("/dodge/{characterId}")
    public ResponseEntity<String> dodgeCharacter(
            @PathVariable("characterId") Long characterId,
            @RequestParam("ninjaType") NinjaType ninjaType) {
        var characterInfo = characterService.dodgeCharacter(characterId, ninjaType);
        return ResponseEntity.ok(characterInfo);
    }
}
