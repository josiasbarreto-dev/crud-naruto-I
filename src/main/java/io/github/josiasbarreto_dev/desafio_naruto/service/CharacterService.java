package io.github.josiasbarreto_dev.desafio_naruto.service;

import io.github.josiasbarreto_dev.desafio_naruto.dto.ChakraRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.JutsuRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.exception.ResourceNotFoundException;
import io.github.josiasbarreto_dev.desafio_naruto.mapper.CharacterMapper;
import io.github.josiasbarreto_dev.desafio_naruto.model.GenjutsuNinja;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjutsuNinja;
import io.github.josiasbarreto_dev.desafio_naruto.model.TaijutsuNinja;
import io.github.josiasbarreto_dev.desafio_naruto.repository.GenjutsuRepository;
import io.github.josiasbarreto_dev.desafio_naruto.repository.NinjutsuRepository;
import io.github.josiasbarreto_dev.desafio_naruto.repository.TaijutsuRepository;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {
    private final GenjutsuRepository genjutsuRepository;
    private final NinjutsuRepository ninjutsuRepository;
    private final TaijutsuRepository taijutsuRepository;

    private final CharacterMapper mapper;

    public CharacterService(GenjutsuRepository genjutsuRepository, NinjutsuRepository ninjutsuRepository, TaijutsuRepository taijutsuRepository, CharacterMapper mapper) {
        this.genjutsuRepository = genjutsuRepository;
        this.ninjutsuRepository = ninjutsuRepository;
        this.taijutsuRepository = taijutsuRepository;
        this.mapper = mapper;
    }

    public CharacterResponseDTO createCharacter(CharacterRequestDTO requestDTO){
        switch (requestDTO.ninjaType()) {
            case GENJUTSU:
                var genjutsuToSave = mapper.toEntityGenjutsu(requestDTO);
                GenjutsuNinja savedCharacterGenjutsu = genjutsuRepository.save(genjutsuToSave);
                return mapper.toDTO(savedCharacterGenjutsu);

            case NINJUTSU:
                var ninjutsuToSave = mapper.toEntityNinjutsu(requestDTO);
                NinjutsuNinja savedCharacterNinjutsu = ninjutsuRepository.save(ninjutsuToSave);
                return mapper.toDTO(savedCharacterNinjutsu);

            case TAIJUTSU:
                var taijutsuToSave = mapper.toEntityTaijutsu(requestDTO);
                TaijutsuNinja savedCharacterTaijutsu = taijutsuRepository.save(taijutsuToSave);
                return mapper.toDTO(savedCharacterTaijutsu);

            default:
                throw new IllegalArgumentException("Ninja type is invalid");
        }
    }

    public CharacterResponseDTO addNewJutsu(Long characterId, JutsuRequestDTO dto) {
        switch (dto.ninjaType()){
            case GENJUTSU:
                var characterGenjutsu = genjutsuRepository.findById(characterId). orElseThrow(() -> new ResourceNotFoundException("Character with ID " + characterId + " not found"));
                characterGenjutsu.addJutsu(dto.jutsuName());
                return mapper.toDTO(genjutsuRepository.save(characterGenjutsu));

            case NINJUTSU:
                var characterNinjutsu = ninjutsuRepository.findById(characterId).orElseThrow(() -> new ResourceNotFoundException("Character with ID " + characterId + " not found"));
                characterNinjutsu.addJutsu(dto.jutsuName());
                return mapper.toDTO(ninjutsuRepository.save(characterNinjutsu));

            case TAIJUTSU:
                var characterTaijutsu = taijutsuRepository.findById(characterId).orElseThrow(() -> new ResourceNotFoundException("Character with ID " + characterId + " not found"));
                characterTaijutsu.addJutsu(dto.jutsuName());
                return mapper.toDTO(taijutsuRepository.save(characterTaijutsu));

            default:
                throw new IllegalArgumentException("Ninja type is invalid");
        }
    }

    public CharacterResponseDTO increaseChakra(Long characterId, ChakraRequestDTO dto) {
        switch (dto.ninjaType()){
            case GENJUTSU:
                var characterGenjutsu = genjutsuRepository.findById(characterId). orElseThrow(() -> new ResourceNotFoundException("Character with ID " + characterId + " not found"));
                characterGenjutsu.increaseChakra(dto.chakraValue());
                return mapper.toDTO(genjutsuRepository.save(characterGenjutsu));

            case NINJUTSU:
                var characterNinjutsu = ninjutsuRepository.findById(characterId).orElseThrow(() -> new ResourceNotFoundException("Character with ID " + characterId + " not found"));
                characterNinjutsu.increaseChakra(dto.chakraValue());
                return mapper.toDTO(ninjutsuRepository.save(characterNinjutsu));

            case TAIJUTSU:
                var characterTaijutsu = taijutsuRepository.findById(characterId).orElseThrow(() -> new ResourceNotFoundException("Character with ID " + characterId + " not found"));
                characterTaijutsu.increaseChakra(dto.chakraValue());
                return mapper.toDTO(taijutsuRepository.save(characterTaijutsu));

            default:
                throw new IllegalArgumentException("Ninja type is invalid");
        }
    }

    public String getDisplayInfo(Long characterId, NinjaType ninjaType){
        switch (ninjaType){
            case GENJUTSU:
                var characterGenjutsu = genjutsuRepository.findById(characterId). orElseThrow(() -> new ResourceNotFoundException("Character with ID " + characterId + " not found"));
                return characterGenjutsu.displayInfo();

            case NINJUTSU:
                var characterNinjutsu = ninjutsuRepository.findById(characterId).orElseThrow(() -> new ResourceNotFoundException("Character with ID " + characterId + " not found"));
                return characterNinjutsu.displayInfo();

            case TAIJUTSU:
                var characterTaijutsu = taijutsuRepository.findById(characterId).orElseThrow(() -> new ResourceNotFoundException("Character with ID " + characterId + " not found"));
                return characterTaijutsu.displayInfo();

            default:
                throw new IllegalArgumentException("Ninja type is invalid");
        }
    }

    public String useJutsu(Long characterId, NinjaType ninjaType){
        switch (ninjaType){
            case GENJUTSU:
                var characterGenjutsu = genjutsuRepository.findById(characterId). orElseThrow(() -> new ResourceNotFoundException("Character with ID " + characterId + " not found"));
                return characterGenjutsu.useJutsu();

            case NINJUTSU:
                var characterNinjutsu = ninjutsuRepository.findById(characterId).orElseThrow(() -> new ResourceNotFoundException("Character with ID " + characterId + " not found"));
                return characterNinjutsu.useJutsu();

            case TAIJUTSU:
                var characterTaijutsu = taijutsuRepository.findById(characterId).orElseThrow(() -> new ResourceNotFoundException("Character with ID " + characterId + " not found"));
                return characterTaijutsu.useJutsu();

            default:
                throw new IllegalArgumentException("Ninja type is invalid");
        }
    }

    public String dodgeCharacter(Long characterId, NinjaType ninjaType){
        switch (ninjaType){
            case GENJUTSU:
                var characterGenjutsu = genjutsuRepository.findById(characterId). orElseThrow(() -> new ResourceNotFoundException("Character with ID " + characterId + " not found"));
                return characterGenjutsu.dodge();

            case NINJUTSU:
                var characterNinjutsu = ninjutsuRepository.findById(characterId).orElseThrow(() -> new ResourceNotFoundException("Character with ID " + characterId + " not found"));
                return characterNinjutsu.dodge();

            case TAIJUTSU:
                var characterTaijutsu = taijutsuRepository.findById(characterId).orElseThrow(() -> new ResourceNotFoundException("Character with ID " + characterId + " not found"));
                return characterTaijutsu.dodge();

            default:
                throw new IllegalArgumentException("Ninja type is invalid");
        }
    }
}

