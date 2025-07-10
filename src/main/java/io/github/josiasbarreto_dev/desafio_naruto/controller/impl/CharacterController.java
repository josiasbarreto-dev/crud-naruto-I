package io.github.josiasbarreto_dev.desafio_naruto.controller.impl;

import io.github.josiasbarreto_dev.desafio_naruto.controller.CharacterControllerInterface;
import io.github.josiasbarreto_dev.desafio_naruto.dto.AttackRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.BattleResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import io.github.josiasbarreto_dev.desafio_naruto.service.impl.CharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class CharacterController implements CharacterControllerInterface {
    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @Override
    public ResponseEntity<CharacterResponseDTO> createCharacter(@RequestBody CharacterRequestDTO requestDTO) {
        var character = characterService.createCharacter(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(character);
    }

    @Override
    public ResponseEntity<List<CharacterResponseDTO>> listCharacter() {
        var listCharacter = characterService.listCharacter();
        return ResponseEntity.ok(listCharacter);
    }

    @Override
    public ResponseEntity<Character> getCharacterById(@PathVariable Long characterId) {
        var characterInfo = characterService.getCharacterById(characterId);
        return ResponseEntity.ok(characterInfo);
    }

    @Override
    public ResponseEntity<List<CharacterResponseDTO>> listCharactersByType(@RequestParam NinjaType ninjaType){
        var listOfCharacterTypes = characterService.listCharactersByType(ninjaType);
        return ResponseEntity.ok(listOfCharacterTypes);
    }

    @Override
    public ResponseEntity<Void> deleteCharacterById(@PathVariable Long characterId){
        characterService.deleteCharacterById(characterId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<BattleResponseDTO> fight(@RequestBody AttackRequestDTO attackRequestDTO){
        var attackResult = characterService.fight(attackRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(attackResult);
    }

    @Override
    public ResponseEntity<CharacterResponseDTO> addChakra(@PathVariable Long ninjaId, @RequestParam int chakraAmount){
        var character = characterService.addChakra(ninjaId, chakraAmount);
        return ResponseEntity.status(HttpStatus.CREATED).body(character);
    }
}
