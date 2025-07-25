package io.github.josiasbarreto_dev.desafio_naruto.controller;

import io.github.josiasbarreto_dev.desafio_naruto.controller.impl.CharacterController;
import io.github.josiasbarreto_dev.desafio_naruto.dto.*;
import io.github.josiasbarreto_dev.desafio_naruto.exception.ResourceNotFoundException;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import io.github.josiasbarreto_dev.desafio_naruto.service.impl.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Controller Unit Tests")
public class CharacterControllerTest {
    private static final Long VALID_CHARACTER_ID = 1L;
    private static final Long INVALID_CHARACTER_ID = 10L;
    private static final NinjaType NINJA_TYPE_TAIJUTSU = NinjaType.TAIJUTSU;
    private static final Integer DEFAULT_CHAKRA = 100;
    private static final Integer DEFAULT_LIFE = 100;
    private static final Integer NARUTO_CHAKRA = 150;
    private static final Integer NARUTO_LIFE = 200;
    private static final Integer DAMAGE_HIGH = 80;
    private static final Integer CHAKRA_COST_HIGH = 60;
    private static final Integer DAMAGE_MEDIUM = 20;
    private static final Integer CHAKRA_COST_MEDIUM = 30;
    private static final Integer DAMAGE_ZERO = 0;
    private static final Integer CHAKRA_COST_FULL = 100;
    @InjectMocks
    private CharacterController characterController;
    @Mock
    private CharacterService characterService;
    private CharacterRequestDTO payloadRequestDTO;
    private CharacterRequestDTO payloadRequestWithNinjaTypeInvalid;
    private CharacterResponseDTO expectedCharacterJiraiya;
    private CharacterResponseDTO expectedCharacterNaruto;
    private List<CharacterResponseDTO> characterListResponseDTO;
    private AttackRequestDTO requestAttackDTO;
    private BattleResponseDTO battleResponseDTO;

    @BeforeEach
    void setup() {
        payloadRequestDTO = buildCharacterRequestDTO("Jiraiya", getDefaultJutsusRequest(), DEFAULT_LIFE, NinjaType.TAIJUTSU);
        payloadRequestWithNinjaTypeInvalid = buildCharacterRequestDTO("Jiraiya", getDefaultJutsusRequest(), DEFAULT_LIFE, NinjaType.INVALID_TYPE);

        expectedCharacterJiraiya = buildCharacterResponseDTO(VALID_CHARACTER_ID, "Jiraiya", getDefaultJutsus(), DEFAULT_CHAKRA, DEFAULT_LIFE);
        expectedCharacterNaruto = buildCharacterResponseDTO(2L, "Naruto Uzumaki", getDefaultJutsus(), NARUTO_CHAKRA, NARUTO_LIFE);

        characterListResponseDTO = getCharacterListResponse();

        requestAttackDTO = new AttackRequestDTO("Jiraiya", "Naruto Uzumaki", "Rasengan");

        battleResponseDTO = buildBattleResponse(expectedCharacterJiraiya, expectedCharacterNaruto);
    }

    private List<JutsuResponseDTO> getDefaultJutsus() {
        return List.of(
                new JutsuResponseDTO(1L, "Rasengan", DAMAGE_HIGH, CHAKRA_COST_HIGH),
                new JutsuResponseDTO(2L, "Summoning Jutsu: Toad", DAMAGE_MEDIUM, CHAKRA_COST_MEDIUM),
                new JutsuResponseDTO(3L, "Sage Mode", DAMAGE_ZERO, CHAKRA_COST_FULL)
        );
    }

    private List<JutsuRequestDTO> getDefaultJutsusRequest() {
        return List.of(
                new JutsuRequestDTO("Rasengan", DAMAGE_HIGH, CHAKRA_COST_HIGH),
                new JutsuRequestDTO("Summoning Jutsu: Toad", DAMAGE_MEDIUM, CHAKRA_COST_MEDIUM),
                new JutsuRequestDTO("Sage Mode", DAMAGE_ZERO, CHAKRA_COST_FULL)
        );
    }

    private CharacterRequestDTO buildCharacterRequestDTO(String name, List<JutsuRequestDTO> jutsus, Integer life, NinjaType type) {
        return new CharacterRequestDTO(name, jutsus, life, type);
    }

    private CharacterResponseDTO buildCharacterResponseDTO(Long id, String name, List<JutsuResponseDTO> jutsus, Integer chakra, Integer life) {
        return new CharacterResponseDTO(id, name, jutsus, chakra, life);
    }

    private List<CharacterResponseDTO> getCharacterListResponse() {
        return List.of(expectedCharacterJiraiya, expectedCharacterNaruto);
    }

    private BattleResponseDTO buildBattleResponse(CharacterResponseDTO attacker, CharacterResponseDTO defender) {
        return new BattleResponseDTO(attacker, defender);
    }

    @Test
    @DisplayName("Deve criar um Personagem e retornar o Status Code 201")
    void shouldCreateCharacterSuccessfullyAndReturn201Created() {
        when(characterService.createCharacter(payloadRequestDTO)).thenReturn(expectedCharacterJiraiya);
        ResponseEntity<CharacterResponseDTO> response = characterController.create(payloadRequestDTO);

        assertEquals(expectedCharacterJiraiya, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verifyNoMoreInteractions(characterService);
    }

    @Test
    @DisplayName("Deve lançar um exceção ao criar um Personagem com Ninja Type inválido")
    void shouldThrowExceptionWhenCreatingCharacterWithInvalidNinjaType() {
        String message = "Ninja type is invalid";
        when(characterService.createCharacter(payloadRequestWithNinjaTypeInvalid)).thenThrow(new IllegalArgumentException(message));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            characterController.create(payloadRequestWithNinjaTypeInvalid);
        });

        assertEquals(message, exception.getMessage());
        verifyNoMoreInteractions(characterService);
    }

    @Test
    @DisplayName("Deve listar todos os Personagens e retornar o Status Code 200")
    void shouldListAllCharactersAndReturnOkStatus() {
        when(characterService.listCharacter()).thenReturn(characterListResponseDTO);

        ResponseEntity<List<CharacterResponseDTO>> response = characterController.list();

        assertEquals(characterListResponseDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verifyNoMoreInteractions(characterService);
    }

    @Test
    @DisplayName("Deve retornar um Ninja específico com status OK quando encontrado")
    void shouldReturnCharacterByIdAnd200OkStatus() {
        when(characterService.getCharacterById(VALID_CHARACTER_ID)).thenReturn(expectedCharacterJiraiya);

        ResponseEntity<CharacterResponseDTO> response = characterController.get(VALID_CHARACTER_ID);

        assertEquals(expectedCharacterJiraiya, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(characterService, times(1)).getCharacterById(VALID_CHARACTER_ID);
        verifyNoMoreInteractions(characterService);
    }

    @Test
    @DisplayName("Deve retornar uma exceção quando o ID do Personagem não for encontrado")
    void shouldThrowResourceNotFoundExceptionWhenCharacterIdIsNotFound() {
        String message = "Ninja with id " + INVALID_CHARACTER_ID + " not found.";
        when(characterService.getCharacterById(INVALID_CHARACTER_ID)).thenThrow(new ResourceNotFoundException(message));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            characterController.get(INVALID_CHARACTER_ID);
        });

        assertEquals(message, exception.getMessage());
        verifyNoMoreInteractions(characterService);
    }

    @Test
    @DisplayName("Deve listar todos os Personagens com o Ninja Type Informado e retornar o Status Code 200")
    void shouldListCharactersByNinjaTypeAndReturnOkStatus() {
        when(characterService.listCharactersByType(NINJA_TYPE_TAIJUTSU)).thenReturn(characterListResponseDTO);

        ResponseEntity<List<CharacterResponseDTO>> response = characterController.list(NINJA_TYPE_TAIJUTSU);

        assertEquals(characterListResponseDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verifyNoMoreInteractions(characterService);
    }

    @Test
    @DisplayName("Deve deletar Personagem com sucesso e retornar 204 No Content")
    void shouldDeleteCharacterSuccessfullyAndReturn204NoContent() {
        doNothing().when(characterService).deleteCharacterById(VALID_CHARACTER_ID);

        ResponseEntity<Void> response = characterController.delete(VALID_CHARACTER_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verifyNoMoreInteractions(characterService);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar deletar Personagem inexistente")
    void shouldThrowResourceNotFoundExceptionWhenDeletingNonExistentCharacter() {
        String message = "Ninja with id " + INVALID_CHARACTER_ID + " not found.";
        doThrow(new ResourceNotFoundException(message))
                .when(characterService).deleteCharacterById(INVALID_CHARACTER_ID);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            characterController.delete(INVALID_CHARACTER_ID);
        });

        assertEquals(message, exception.getMessage());
        verifyNoMoreInteractions(characterService);
    }

    @Test
    @DisplayName("Deve adicionar o novo chakra e retornar sucesso")
    void shouldAddNewChakraAndReturnSuccess() {
        Integer chakraAmount = 100;

        when(characterService.addChakra(VALID_CHARACTER_ID, chakraAmount)).thenReturn(expectedCharacterJiraiya);

        ResponseEntity<CharacterResponseDTO> response = characterController.addChakra(VALID_CHARACTER_ID, chakraAmount);

        assertEquals(expectedCharacterJiraiya, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verifyNoMoreInteractions(characterService);
    }


    @Test
    @DisplayName("Deve retornar Resultado da Batalha e Status 200 OK quando a Luta Ocorre.")
    void shouldReturnBattleResultAnd200OkStatusWhenFightOccurs() {
        when(characterService.fight(requestAttackDTO)).thenReturn(battleResponseDTO);

        ResponseEntity<BattleResponseDTO> response = characterController.fight(requestAttackDTO);

        assertEquals(battleResponseDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verifyNoMoreInteractions(characterService);
    }
}
