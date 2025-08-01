package io.github.josiasbarreto_dev.desafio_naruto.service.impl;

import io.github.josiasbarreto_dev.desafio_naruto.dto.AttackRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.BattleResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.exception.NameAlreadyExistsException;
import io.github.josiasbarreto_dev.desafio_naruto.exception.ResourceNotFoundException;
import io.github.josiasbarreto_dev.desafio_naruto.mapper.CharacterMapper;
import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import io.github.josiasbarreto_dev.desafio_naruto.model.Jutsu;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import io.github.josiasbarreto_dev.desafio_naruto.repository.CharacterRepository;
import io.github.josiasbarreto_dev.desafio_naruto.service.CharacterServiceInterface;
import io.github.josiasbarreto_dev.desafio_naruto.service.strategy.NinjaFactoryContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService implements CharacterServiceInterface {
    private final CharacterRepository repository;
    private final CharacterMapper mapper;
    private final NinjaFactoryContext ninjaFactoryContext;


    public CharacterService(CharacterRepository repository, CharacterMapper mapper, NinjaFactoryContext ninjaFactoryContext) {
        this.repository = repository;
        this.mapper = mapper;
        this.ninjaFactoryContext = ninjaFactoryContext;
    }

    @Override
    public CharacterResponseDTO createCharacter(CharacterRequestDTO requestDTO) {
        if (repository.existsByName(requestDTO.name())) {
            throw new NameAlreadyExistsException("There is already a registered character with this name: " + requestDTO.name());
        }

        Character character = ninjaFactoryContext.create(
                requestDTO.ninjaType(),
                requestDTO.name(),
                requestDTO.life()
        );

        if (requestDTO.jutsus() != null) {
            requestDTO.jutsus().forEach((jutsuDTO -> {
                Jutsu jutsu = new Jutsu(
                        jutsuDTO.name(),
                        jutsuDTO.damage(),
                        jutsuDTO.chakraConsumption()
                );
                character.addJutsu(jutsu);
            }));
        }

        return mapper.toDTO(repository.save(character));
    }

    @Override
    public List<CharacterResponseDTO> listCharacter() {
        var listCharacter = repository.findAll();
        return mapper.toDTOCharacters(listCharacter);
    }

    @Override
    public CharacterResponseDTO getCharacterById(Long id) {
        return mapper.toDTO(findCharacterById(id));
    }

    @Override
    public List<CharacterResponseDTO> listCharactersByType(NinjaType ninjaType) {
        return repository.findAll().stream()
                .filter(character -> character.getType().equals(ninjaType))
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public void deleteCharacterById(Long id) {
        repository.delete(findCharacterById(id));
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
    public CharacterResponseDTO addChakra(Long id, Integer chakraAmount) {
        var character = findCharacterById(id);
        character.setChakra(chakraAmount);

        var updatedCharacter = saveCharacter(character);

        return mapper.toDTO(updatedCharacter);
    }

    private Character findCharacterById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ninja with id " + id + " not found."));
    }

    private Character findCharacterByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Ninja with name " + name + " not found."));
    }

    private Character saveCharacter(Character character) {
        return repository.save(character);
    }
}

