package io.github.josiasbarreto_dev.desafio_naruto.service.impl;

import io.github.josiasbarreto_dev.desafio_naruto.dto.AttackRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.BattleResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.exception.NameAlreadyExistsException;
import io.github.josiasbarreto_dev.desafio_naruto.exception.ResourceNotFoundException;
import io.github.josiasbarreto_dev.desafio_naruto.mapper.CharacterMapper;
import io.github.josiasbarreto_dev.desafio_naruto.model.*;
import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import io.github.josiasbarreto_dev.desafio_naruto.repository.CharacterRepository;
import io.github.josiasbarreto_dev.desafio_naruto.service.CharacterServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService implements CharacterServiceInterface {
    private final CharacterRepository repository;
    private final CharacterMapper mapper;

    public CharacterService(CharacterRepository repository, CharacterMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CharacterResponseDTO createCharacter(CharacterRequestDTO requestDTO) {
        if (repository.existsByName(requestDTO.name())) {
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

    @Override
    public List<CharacterResponseDTO> listCharacter() {
        var listCharacter = repository.findAll();
        return mapper.toDTO(listCharacter);
    }

    @Override
    public CharacterResponseDTO getCharacterById(Long characterId) {
        return mapper.toDTO(findCharacterById(characterId));
    }

    @Override
    public List<CharacterResponseDTO> listCharactersByType(NinjaType ninjaType) {
        return repository.findAll().stream().filter(character -> {
            return switch (ninjaType) {
                case NINJUTSU -> character instanceof NinjutsuNinja;
                case TAIJUTSU -> character instanceof TaijutsuNinja;
                case INVALID_TYPE -> throw new IllegalArgumentException("Ninja type is invalid");
            };
        }).map(mapper::toDTO).toList();
    }

    @Override
    public void deleteCharacterById(Long characterId) {
        repository.delete(findCharacterById(characterId));
    }

    @Override
    public BattleResponseDTO fight(AttackRequestDTO dto) {
        var attacker = findCharacterByName(dto.attacker());
        var target = findCharacterByName(dto.target());

        attacker.useJutsu(dto.jutsu(), target);

        Character createdAttacker = saveCharacter(attacker);
        Character createdTarget = saveCharacter(target);

        return new BattleResponseDTO(mapper.toDTO(createdAttacker), mapper.toDTO(createdTarget));
    }

    @Override
    public CharacterResponseDTO addChakra(Long ninjaId, Integer chakraAmount) {
        var character = findCharacterById(ninjaId);
        character.setChakra(chakraAmount);

        var updatedCharacter = saveCharacter(character);

        return mapper.toDTO(updatedCharacter);
    }

    private Character findCharacterById(Long characterId) {
        return repository.findById(characterId)
                .orElseThrow(() -> new ResourceNotFoundException("Ninja with id " + characterId + " not found."));
    }

    private Character findCharacterByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Ninja with name " + name + " not found."));
    }

    private Character saveCharacter(Character character) {
        return repository.save(character);
    }
}

