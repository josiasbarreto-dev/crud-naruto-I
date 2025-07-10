package io.github.josiasbarreto_dev.desafio_naruto.service;

import io.github.josiasbarreto_dev.desafio_naruto.dto.*;
import io.github.josiasbarreto_dev.desafio_naruto.exception.NameAlreadyExistsException;
import io.github.josiasbarreto_dev.desafio_naruto.exception.ResourceNotFoundException;
import io.github.josiasbarreto_dev.desafio_naruto.mapper.CharacterMapper;
import io.github.josiasbarreto_dev.desafio_naruto.model.*;
import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import io.github.josiasbarreto_dev.desafio_naruto.repository.CharacterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {
    private final CharacterRepository repository;
    private final CharacterMapper mapper;

    public CharacterService(CharacterRepository repository, CharacterMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public CharacterResponseDTO createCharacter(CharacterRequestDTO requestDTO){
        if(repository.existsByName(requestDTO.name())){
            throw new NameAlreadyExistsException("There is already a registered character with this name: " + requestDTO.name());
        }
        Character character = switch (requestDTO.ninjaType()) {
            case NINJUTSU -> new NinjutsuNinja(requestDTO.name(), requestDTO.life());
            case TAIJUTSU -> new TaijutsuNinja(requestDTO.name(), requestDTO.life());
            default -> throw new IllegalArgumentException("Ninja type is invalid");
        };

        if (requestDTO.jutsus() != null) {
            requestDTO.jutsus().forEach((jutsuName, jutsuDTO) -> {
                Jutsu jutsu = new Jutsu(jutsuDTO.damage(), jutsuDTO.chakraConsumption());
                character.addJutsu(jutsuName, jutsu);
            });
        }

        return mapper.toDTO(repository.save(character));
    }

    public List<CharacterResponseDTO> listCharacter(){
        var listCharacter = repository.findAll();
        return mapper.toDTO(listCharacter);
    }

    public Character getCharacterById(Long characterId) {
        return repository.findById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("Ninja with id " + characterId + " not found."));
    }

    public List<CharacterResponseDTO> listCharactersByType(NinjaType ninjaType){
        return repository.findAll()
                .stream()
                .filter(character -> {
                    return switch (ninjaType) {
                        case NINJUTSU -> character instanceof NinjutsuNinja;
                        case TAIJUTSU -> character instanceof TaijutsuNinja;
                        case INVALID_TYPE -> throw new IllegalArgumentException("Ninja type is invalid");
                    };
                })
                .map(mapper::toDTO)
                .toList();
    }

    public void deleteCharacterById(Long characterId){
        var character = repository.findById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("Ninja with id " + characterId + " not found."));
        repository.delete(character);
    }

    @Transactional
    public BattleResponseDTO fight(AttackRequestDTO dto){
        var attacker = repository.findByName(dto.attacker())
                .orElseThrow(() -> new ResourceNotFoundException("Ninja with name " + dto.attacker() + " not found."));
        var target = repository.findByName(dto.target())
                .orElseThrow(() -> new ResourceNotFoundException("Ninja with name " + dto.target() + " not found."));

        attacker.useJutsu(dto.jutsu(), target);

        repository.save(attacker);
        repository.save(target);

        return new BattleResponseDTO(mapper.toDTO(attacker), mapper.toDTO(target));
    }

    public CharacterResponseDTO addChakra(Long ninjaId, int chakraAmount){
        var character = getCharacterById(ninjaId);
        character.setChakra(chakraAmount);

        var updatedCharacter = repository.save(character);

        return mapper.toDTO(updatedCharacter);
    }
}

