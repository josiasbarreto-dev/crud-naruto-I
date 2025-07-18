package io.github.josiasbarreto_dev.desafio_naruto.controller.impl;

import io.github.josiasbarreto_dev.desafio_naruto.controller.CharacterControllerInterface;
import io.github.josiasbarreto_dev.desafio_naruto.dto.AttackRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.BattleResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import io.github.josiasbarreto_dev.desafio_naruto.service.CharacterServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CharacterController implements CharacterControllerInterface {
    private final CharacterServiceInterface characterService;

    public CharacterController(CharacterServiceInterface characterService) {
        this.characterService = characterService;
    }

    @Override
    public ResponseEntity<CharacterResponseDTO> create(@RequestBody CharacterRequestDTO requestDTO) {
        var character = characterService.createCharacter(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(character);
    }

    @Override
    public ResponseEntity<List<CharacterResponseDTO>> list() {
        var listCharacter = characterService.listCharacter();
        return ResponseEntity.ok(listCharacter);
    }

    @Override
    public ResponseEntity<CharacterResponseDTO> get(@PathVariable Long id) {
        var character = characterService.getCharacterById(id);
        return ResponseEntity.ok(character);
    }

    @Override
    public ResponseEntity<List<CharacterResponseDTO>> list(@RequestParam NinjaType ninjaType){
        var listOfCharacterTypes = characterService.listCharactersByType(ninjaType);
        return ResponseEntity.ok(listOfCharacterTypes);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id){
        characterService.deleteCharacterById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<BattleResponseDTO> fight(@RequestBody AttackRequestDTO attackRequestDTO){
        var attackResult = characterService.fight(attackRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(attackResult);
    }

    @Override
    public ResponseEntity<CharacterResponseDTO> addChakra(@PathVariable Long id, @RequestParam Integer chakraAmount){
        var character = characterService.addChakra(id, chakraAmount);
        return ResponseEntity.status(HttpStatus.CREATED).body(character);
    }
}
