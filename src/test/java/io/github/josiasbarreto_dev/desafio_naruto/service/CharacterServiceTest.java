package io.github.josiasbarreto_dev.desafio_naruto.service;

import io.github.josiasbarreto_dev.desafio_naruto.dto.AttackRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.BattleResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.JutsuRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.exception.NameAlreadyExistsException;
import io.github.josiasbarreto_dev.desafio_naruto.exception.ResourceNotFoundException;
import io.github.josiasbarreto_dev.desafio_naruto.mapper.CharacterMapper;
import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import io.github.josiasbarreto_dev.desafio_naruto.model.Jutsu;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjutsuNinja;
import io.github.josiasbarreto_dev.desafio_naruto.model.TaijutsuNinja;
import io.github.josiasbarreto_dev.desafio_naruto.repository.CharacterRepository;
import io.github.josiasbarreto_dev.desafio_naruto.service.impl.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CharacterService Unit Tests")
class CharacterServiceTest {

    @InjectMocks
    private CharacterService characterService;

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private CharacterMapper characterMapper;

    private static final Long VALID_CHARACTER_ID = 1L;
    private static final Long ANOTHER_VALID_ID = 2L;
    private static final Long INVALID_CHARACTER_ID = 10L;

    private CharacterRequestDTO jiraiyaRequestDTO;
    private CharacterRequestDTO taijutsuRequestDTO;
    private CharacterRequestDTO invalidNinjaTypeRequestDTO;
    private AttackRequestDTO attackRequestDTO;

    private Character jiraiyaEntity;
    private Character narutoEntity;
    private Character rockLeeEntity;

    private CharacterResponseDTO jiraiyaResponseDTO;
    private CharacterResponseDTO narutoResponseDTO;
    private CharacterResponseDTO rockLeeResponseDTO;

    private List<CharacterResponseDTO> characterListResponseDTO;

    @BeforeEach
    void setUp() {
        jiraiyaRequestDTO = new CharacterRequestDTO(
                "Jiraiya",
                Map.of(
                        "Rasengan", new JutsuRequestDTO(80, 60),
                        "Summoning Jutsu", new JutsuRequestDTO(20, 30),
                        "Sage Mode", new JutsuRequestDTO(0, 100)
                ),
                100,
                NinjaType.NINJUTSU
        );

        taijutsuRequestDTO = new CharacterRequestDTO(
                "Rock Lee",
                Map.of("Primary Lotus", new JutsuRequestDTO(70, 50)),
                90,
                NinjaType.TAIJUTSU
        );

        invalidNinjaTypeRequestDTO = new CharacterRequestDTO(
                "InvalidNinja",
                Map.of("Basic Punch", new JutsuRequestDTO(10, 0)),
                50,
                NinjaType.INVALID_TYPE
        );

        attackRequestDTO = new AttackRequestDTO(
                "Jiraiya",
                "Naruto Uzumaki",
                "Rasengan"
        );

        jiraiyaEntity = new NinjutsuNinja("Jiraiya", 100);
        jiraiyaEntity.setId(VALID_CHARACTER_ID);
        jiraiyaEntity.addJutsu("Rasengan", new Jutsu(80, 60));
        jiraiyaEntity.addJutsu("Summoning Jutsu: Toad", new Jutsu(20, 30));
        jiraiyaEntity.addJutsu("Sage Mode", new Jutsu(0, 100));



        narutoEntity = new NinjutsuNinja("Naruto Uzumaki", 150);
        narutoEntity.setId(ANOTHER_VALID_ID);
        narutoEntity.addJutsu("Shadow Clone Jutsu", new Jutsu(50, 40));
        narutoEntity.addJutsu("Rasengan", new Jutsu(90, 70));
        narutoEntity.addJutsu("Sage Mode", new Jutsu(0, 120));

        narutoEntity.setLife(150);

        rockLeeEntity = new TaijutsuNinja("Rock Lee", 90);
        rockLeeEntity.setId(3L);
        rockLeeEntity.addJutsu("Primary Lotus", new Jutsu(70, 50));


        jiraiyaResponseDTO = new CharacterResponseDTO(
                VALID_CHARACTER_ID,
                "Jiraiya",
                Map.of(
                        "Rasengan", new Jutsu(80, 60),
                        "Summoning Jutsu: Toad", new Jutsu(20, 30),
                        "Sage Mode", new Jutsu(0, 100)
                ),
                100,
                100
        );

        narutoResponseDTO = new CharacterResponseDTO(
                ANOTHER_VALID_ID,
                "Naruto Uzumaki",
                Map.of(
                        "Shadow Clone Jutsu", new Jutsu(50, 40),
                        "Rasengan", new Jutsu(90, 70),
                        "Sage Mode", new Jutsu(0, 120)
                ),
                100,
                200
        );

        rockLeeResponseDTO = new CharacterResponseDTO(
                3L,
                "Rock Lee",
                Map.of("Primary Lotus", new Jutsu(70, 50)),
                100,
                90
        );

        characterListResponseDTO = List.of(jiraiyaResponseDTO, narutoResponseDTO);
    }

    @Test
    @DisplayName("Deve criar personagem Ninjutsu com sucesso")
    void shouldCreateNinjutsuCharacterSuccessfully() {
        when(characterRepository.existsByName(jiraiyaRequestDTO.name())).thenReturn(false);

        when(characterRepository.save(any(NinjutsuNinja.class))).thenReturn((NinjutsuNinja) jiraiyaEntity);
        when(characterMapper.toDTO(jiraiyaEntity)).thenReturn(jiraiyaResponseDTO);

        CharacterResponseDTO result = characterService.createCharacter(jiraiyaRequestDTO);

        assertNotNull(result);
        assertEquals(jiraiyaResponseDTO, result);
        assertEquals(jiraiyaRequestDTO.name(), result.name());
        assertEquals(jiraiyaRequestDTO.life(), result.life());
        assertEquals(jiraiyaRequestDTO.jutsus().size(), result.jutsus().size());
        assertTrue(result.jutsus().containsKey("Rasengan"));

        verify(characterRepository, times(1)).existsByName(jiraiyaRequestDTO.name());
        verify(characterRepository, times(1)).save(isA(NinjutsuNinja.class));
        verify(characterMapper, times(1)).toDTO(jiraiyaEntity);
    }

    @Test
    @DisplayName("Deve criar personagem Taijutsu com sucesso")
    void shouldCreateTaijutsuCharacterSuccessfully() {
        when(characterRepository.existsByName(taijutsuRequestDTO.name())).thenReturn(false);
        when(characterRepository.save(any(TaijutsuNinja.class))).thenReturn((TaijutsuNinja) rockLeeEntity);
        when(characterMapper.toDTO(rockLeeEntity)).thenReturn(rockLeeResponseDTO);

        CharacterResponseDTO result = characterService.createCharacter(taijutsuRequestDTO);

        assertNotNull(result);
        assertEquals(rockLeeResponseDTO, result);
        assertEquals(taijutsuRequestDTO.name(), result.name());

        verify(characterRepository, times(1)).existsByName(taijutsuRequestDTO.name());
        verify(characterRepository, times(1)).save(isA(TaijutsuNinja.class));
        verify(characterMapper, times(1)).toDTO(rockLeeEntity);
    }

    @Test
    @DisplayName("Deve lançar NameAlreadyExistsException quando nome de personagem já existe")
    void shouldThrowNameAlreadyExistsExceptionWhenCharacterNameAlreadyExists() {
        when(characterRepository.existsByName(jiraiyaRequestDTO.name())).thenReturn(true);

        NameAlreadyExistsException exception = assertThrows(NameAlreadyExistsException.class, () -> {
            characterService.createCharacter(jiraiyaRequestDTO);
        });

        String message = "There is already a registered character with this name: " + jiraiyaRequestDTO.name();
        assertEquals(message, exception.getMessage());
        verify(characterRepository, times(1)).existsByName(jiraiyaRequestDTO.name());
        verify(characterRepository, never()).save(any(Character.class));
        verify(characterMapper, never()).toDTO(any(Character.class));
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando tipo de ninja for inválido na criação")
    void shouldThrowIllegalArgumentExceptionWhenNinjaTypeIsInvalidOnCreation() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            characterService.createCharacter(invalidNinjaTypeRequestDTO);
        });

        String message = "Ninja type is invalid";
        assertEquals(message, exception.getMessage());
        verify(characterRepository, times(1)).existsByName("InvalidNinja");
        verify(characterRepository, never()).save(any(Character.class));
        verify(characterMapper, never()).toDTO(any(Character.class));
    }

    @Test
    @DisplayName("Deve Listar Todos os Personagens com Sucesso")
    void shouldListAllCharactersSuccessfully() {
        List<Character> listCharacters = List.of(jiraiyaEntity, narutoEntity);
        when(characterRepository.findAll()).thenReturn(listCharacters);
        when(characterMapper.toDTO(listCharacters)).thenReturn(characterListResponseDTO);

        List<CharacterResponseDTO> result = characterService.listCharacter();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(characterListResponseDTO, result);

        verify(characterRepository, times(1)).findAll();
        verify(characterMapper, times(1)).toDTO(listCharacters);
    }

    @Test
    @DisplayName("Deve Retornar Lista Vazia Quando Nenhum Personagem Existir")
    void shouldReturnEmptyListWhenNoCharactersExist() {
        when(characterRepository.findAll()).thenReturn(Collections.emptyList());
        when(characterMapper.toDTO(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<CharacterResponseDTO> result = characterService.listCharacter();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(characterRepository, times(1)).findAll();
        verify(characterMapper, times(1)).toDTO(Collections.emptyList());
    }

    @Test
    @DisplayName("Deve Retornar Personagem por ID Quando Encontrado")
    void shouldReturnCharacterByIdWhenFound() {
        when(characterRepository.findById(VALID_CHARACTER_ID)).thenReturn(Optional.of(jiraiyaEntity));
        when(characterMapper.toDTO(jiraiyaEntity)).thenReturn(jiraiyaResponseDTO);

        CharacterResponseDTO result = characterService.getCharacterById(VALID_CHARACTER_ID);

        assertNotNull(result);
        assertEquals(jiraiyaResponseDTO, result);
        verify(characterRepository, times(1)).findById(VALID_CHARACTER_ID);
        verify(characterMapper, times(1)).toDTO(jiraiyaEntity);
    }

    @Test
    @DisplayName("Deve Lançar ResourceNotFoundException Quando Personagem por ID Não For Encontrado")
    void shouldThrowResourceNotFoundExceptionWhenCharacterByIdNotFound() {
        when(characterRepository.findById(INVALID_CHARACTER_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            characterService.getCharacterById(INVALID_CHARACTER_ID);
        });

        String message = "Ninja with id " + INVALID_CHARACTER_ID + " not found.";
        assertEquals(message, exception.getMessage());
        verify(characterRepository, times(1)).findById(INVALID_CHARACTER_ID);
        verify(characterMapper, never()).toDTO(any(Character.class));
    }

    @Test
    @DisplayName("Deve Listar Personagens por Tipo Ninjutsu com Sucesso")
    void shouldListCharactersByTypeNinjutsuSuccessfully() {
        List<Character> listCharacters = List.of(jiraiyaEntity, narutoEntity, rockLeeEntity);
        List<CharacterResponseDTO> ninjutsuResponseDTOsExpected = List.of(jiraiyaResponseDTO, narutoResponseDTO);

        when(characterRepository.findAll()).thenReturn(listCharacters);

        when(characterMapper.toDTO(jiraiyaEntity)).thenReturn(jiraiyaResponseDTO);
        when(characterMapper.toDTO(narutoEntity)).thenReturn(narutoResponseDTO);

        List<CharacterResponseDTO> result = characterService.listCharactersByType(NinjaType.NINJUTSU);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(ninjutsuResponseDTOsExpected) && ninjutsuResponseDTOsExpected.containsAll(result));
        assertTrue(result.stream().allMatch(c -> c.name().equals("Jiraiya") || c.name().equals("Naruto Uzumaki")));

        verify(characterRepository, times(1)).findAll();
        verify(characterMapper, times(1)).toDTO(jiraiyaEntity);
        verify(characterMapper, times(1)).toDTO(narutoEntity);
        verify(characterMapper, never()).toDTO(rockLeeEntity);
    }

    @Test
    @DisplayName("Deve Listar Personagens por Tipo Taijutsu com Sucesso")
    void shouldListCharactersByTypeTaijutsuSuccessfully() {
        List<Character> listCharacters = List.of(jiraiyaEntity, narutoEntity, rockLeeEntity);

        when(characterRepository.findAll()).thenReturn(listCharacters);
        when(characterMapper.toDTO(rockLeeEntity)).thenReturn(rockLeeResponseDTO);

        List<CharacterResponseDTO> result = characterService.listCharactersByType(NinjaType.TAIJUTSU);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(rockLeeResponseDTO, result.get(0));
        assertTrue(result.stream().allMatch(c -> c.name().equals("Rock Lee")));

        verify(characterRepository, times(1)).findAll();
        verify(characterMapper, times(1)).toDTO(rockLeeEntity);
        verify(characterMapper, never()).toDTO(jiraiyaEntity);
        verify(characterMapper, never()).toDTO(narutoEntity);
    }

    @Test
    @DisplayName("Deve Retornar Lista Vazia Quando Nenhum Personagem do Tipo Fornecido Existir")
    void shouldReturnEmptyListWhenNoCharactersOfGivenTypeExist() {
        List<Character> allCharacters = List.of(jiraiyaEntity, narutoEntity);
        when(characterRepository.findAll()).thenReturn(allCharacters);

        List<CharacterResponseDTO> result = characterService.listCharactersByType(NinjaType.TAIJUTSU);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(characterRepository, times(1)).findAll();
        verify(characterMapper, never()).toDTO(any(Character.class));
    }

    @Test
    @DisplayName("Deve Deletar Personagem com Sucesso")
    void shouldDeleteCharacterSuccessfully() {
        when(characterRepository.findById(VALID_CHARACTER_ID)).thenReturn(Optional.of(jiraiyaEntity));
        doNothing().when(characterRepository).delete(jiraiyaEntity);

        characterService.deleteCharacterById(VALID_CHARACTER_ID);

        verify(characterRepository, times(1)).findById(VALID_CHARACTER_ID);
        verify(characterRepository, times(1)).delete(jiraiyaEntity);
    }

    @Test
    @DisplayName("Deve Lançar ResourceNotFoundException ao Deletar Personagem Inexistente")
    void shouldThrowResourceNotFoundExceptionWhenDeletingNonExistentCharacter() {
        when(characterRepository.findById(INVALID_CHARACTER_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            characterService.deleteCharacterById(INVALID_CHARACTER_ID);
        });

        String message = "Ninja with id " + INVALID_CHARACTER_ID + " not found.";
        assertEquals(message, exception.getMessage());
        verify(characterRepository, times(1)).findById(INVALID_CHARACTER_ID);
        verify(characterRepository, never()).delete(any(Character.class));
    }

    @Test
    @DisplayName("Deve Realizar Luta e Retornar BattleResponseDTO")
    void shouldPerformFightAndReturnBattleResponseDTO() {
        when(characterRepository.findByName(attackRequestDTO.attacker())).thenReturn(Optional.of(jiraiyaEntity));
        when(characterRepository.findByName(attackRequestDTO.target())).thenReturn(Optional.of(narutoEntity));

        when(characterRepository.save(any(Character.class))).thenAnswer(invocation -> {
            Character savedChar = invocation.getArgument(0);
            if (savedChar.getId().equals(jiraiyaEntity.getId())) {
                savedChar.setChakra(40);
            } else if (savedChar.getId().equals(narutoEntity.getId())) {
                savedChar.setLife(70);
            }
            return savedChar;
        });

        CharacterResponseDTO jiraiyaResponseAfterFight = new CharacterResponseDTO(
                VALID_CHARACTER_ID, "Jiraiya", jiraiyaResponseDTO.jutsus(), jiraiyaResponseDTO.life(), 40);
        CharacterResponseDTO narutoResponseAfterFight = new CharacterResponseDTO(
                ANOTHER_VALID_ID, "Naruto Uzumaki", narutoResponseDTO.jutsus(), 70, narutoResponseDTO.chakra());

        when(characterMapper.toDTO(jiraiyaEntity)).thenReturn(jiraiyaResponseAfterFight);
        when(characterMapper.toDTO(narutoEntity)).thenReturn(narutoResponseAfterFight);

        BattleResponseDTO result = characterService.fight(attackRequestDTO);

        assertNotNull(result);
        assertEquals(jiraiyaResponseAfterFight, result.attacker());
        assertEquals(narutoResponseAfterFight, result.defender());

        verify(characterRepository, times(1)).findByName(attackRequestDTO.attacker());
        verify(characterRepository, times(1)).findByName(attackRequestDTO.target());
        verify(characterRepository, times(2)).save(any(Character.class));
        verify(characterMapper, times(1)).toDTO(jiraiyaEntity);
        verify(characterMapper, times(1)).toDTO(narutoEntity);
    }


    @Test
    @DisplayName("Deve Lançar ResourceNotFoundException Quando o Atacante Não For Encontrado na Luta")
    void shouldThrowResourceNotFoundExceptionWhenAttackerNotFoundInFight() {
        when(characterRepository.findByName(attackRequestDTO.attacker())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            characterService.fight(attackRequestDTO);
        });

        String message = "Ninja with name " + attackRequestDTO.attacker() + " not found.";
        assertEquals(message, exception.getMessage());
        verify(characterRepository, times(1)).findByName(attackRequestDTO.attacker());
        verify(characterRepository, never()).findByName(attackRequestDTO.target());
        verify(characterRepository, never()).save(any(Character.class));
    }

    @Test
    @DisplayName("Deve Lançar ResourceNotFoundException Quando o Alvo Não For Encontrado na Luta")
    void shouldThrowResourceNotFoundExceptionWhenTargetNotFoundInFight() {
        when(characterRepository.findByName(attackRequestDTO.attacker())).thenReturn(Optional.of(jiraiyaEntity));
        when(characterRepository.findByName(attackRequestDTO.target())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            characterService.fight(attackRequestDTO);
        });

        assertEquals("Ninja with name " + attackRequestDTO.target() + " not found.", exception.getMessage());
        verify(characterRepository, times(1)).findByName(attackRequestDTO.attacker());
        verify(characterRepository, times(1)).findByName(attackRequestDTO.target());
        verify(characterRepository, never()).save(any(Character.class));
    }

    @Test
    @DisplayName("Deve Adicionar Chakra com Sucesso")
    void shouldAddChakraSuccessfully() {
        Integer chakraAmount = 50;

        when(characterRepository.findById(VALID_CHARACTER_ID)).thenReturn(Optional.of(jiraiyaEntity));

        when(characterRepository.save(any(Character.class))).thenAnswer(invocation -> {
            Character characterToSave = invocation.getArgument(0);
            characterToSave.setChakra(chakraAmount);
            return characterToSave;
        });

        CharacterResponseDTO expectedResponseDTOAfterChakraAdd = new CharacterResponseDTO(
                VALID_CHARACTER_ID,
                "Jiraiya",
                Map.of(
                        "Rasengan", new Jutsu(80, 60),
                        "Summoning Jutsu: Toad", new Jutsu(20, 30),
                        "Sage Mode", new Jutsu(0, 100)
                ),
                100,
                chakraAmount
        );
        when(characterMapper.toDTO(jiraiyaEntity)).thenReturn(expectedResponseDTOAfterChakraAdd);

        CharacterResponseDTO result = characterService.addChakra(VALID_CHARACTER_ID, chakraAmount);

        assertNotNull(result);
        assertEquals(expectedResponseDTOAfterChakraAdd, result);

        verify(characterRepository, times(1)).findById(VALID_CHARACTER_ID);
        verify(characterRepository, times(1)).save(any(Character.class));
        verify(characterMapper, times(1)).toDTO(jiraiyaEntity);
    }
}