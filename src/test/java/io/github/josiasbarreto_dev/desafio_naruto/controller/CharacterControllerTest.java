package io.github.josiasbarreto_dev.desafio_naruto.controller;

import io.github.josiasbarreto_dev.desafio_naruto.controller.impl.CharacterController;
import io.github.josiasbarreto_dev.desafio_naruto.dto.*;
import io.github.josiasbarreto_dev.desafio_naruto.exception.ResourceNotFoundException;
import io.github.josiasbarreto_dev.desafio_naruto.model.Jutsu;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Controller Unit Tests")
public class CharacterControllerTest {
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

    private static final Long VALID_CHARACTER_ID = 1L;
    private static final Long INVALID_CHARACTER_ID = 10L;
    private static final NinjaType NINJA_TYPE_TAIJUTSU = NinjaType.TAIJUTSU;

    private static final int DEFAULT_CHAKRA = 100;
    private static final int DEFAULT_LIFE = 100;
    private static final int NARUTO_CHAKRA = 150;
    private static final int NARUTO_LIFE = 200;

    private static final int DAMAGE_HIGH = 80;
    private static final int CHAKRA_COST_HIGH = 60;
    private static final int DAMAGE_MEDIUM = 20;
    private static final int CHAKRA_COST_MEDIUM = 30;
    private static final int DAMAGE_ZERO = 0;
    private static final int CHAKRA_COST_FULL = 100;

    @BeforeEach
    void setup() {
        payloadRequestDTO = buildCharacterRequestDTO("Jiraiya", getDefaultJutsusRequest(), DEFAULT_CHAKRA, NinjaType.TAIJUTSU);
        payloadRequestWithNinjaTypeInvalid = buildCharacterRequestDTO("Jiraiya", getDefaultJutsusRequest(), DEFAULT_CHAKRA, NinjaType.INVALID_TYPE);

        expectedCharacterJiraiya = buildCharacterResponseDTO(VALID_CHARACTER_ID, "Jiraiya", getDefaultJutsus(), DEFAULT_CHAKRA, DEFAULT_LIFE);
        expectedCharacterNaruto = buildCharacterResponseDTO(2L, "Naruto Uzumaki", getDefaultJutsus(), NARUTO_CHAKRA, NARUTO_LIFE);

        characterListResponseDTO = getCharacterListResponse();

        requestAttackDTO = new AttackRequestDTO("Jiraiya", "Naruto Uzumaki", "Rasengan");

        battleResponseDTO = buildBattleResponse(expectedCharacterJiraiya, expectedCharacterNaruto);
    }

    private Map<String, Jutsu> getDefaultJutsus() {
        return Map.of(
                "Rasengan", new Jutsu(DAMAGE_HIGH, CHAKRA_COST_HIGH),
                "Summoning Jutsu: Toad", new Jutsu(DAMAGE_MEDIUM, CHAKRA_COST_MEDIUM),
                "Sage Mode", new Jutsu(DAMAGE_ZERO, CHAKRA_COST_FULL)
        );
    }

    private Map<String, JutsuRequestDTO> getDefaultJutsusRequest() {
        return Map.of(
                "Rasengan", new JutsuRequestDTO(DAMAGE_HIGH, CHAKRA_COST_HIGH),
                "Summoning Jutsu: Toad", new JutsuRequestDTO(DAMAGE_MEDIUM, CHAKRA_COST_MEDIUM),
                "Sage Mode", new JutsuRequestDTO(DAMAGE_ZERO, CHAKRA_COST_FULL)
        );
    }

    private CharacterRequestDTO buildCharacterRequestDTO(String name, Map<String, JutsuRequestDTO> jutsus, int chakra, NinjaType type) {
        return new CharacterRequestDTO(name, jutsus, chakra, type);
    }

    private CharacterResponseDTO buildCharacterResponseDTO(Long id, String name, Map<String, Jutsu> jutsus, int chakra, int life) {
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
        ResponseEntity<CharacterResponseDTO> response = characterController.createCharacter(payloadRequestDTO);

        assertEquals(expectedCharacterJiraiya, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(characterService, times(1)).createCharacter(payloadRequestDTO);
    }

    @Test
    @DisplayName("Deve lançar um exceção ao criar um Personagem com Ninja Type inválido")
    void shouldThrowExceptionWhenCreatingCharacterWithInvalidNinjaType() {
        String message = "Ninja type is invalid";
        when(characterService.createCharacter(payloadRequestWithNinjaTypeInvalid)).thenThrow(new IllegalArgumentException(message));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            characterController.createCharacter(payloadRequestWithNinjaTypeInvalid);
        });

        assertEquals(message, exception.getMessage());
        verify(characterService, times(1)).createCharacter(payloadRequestWithNinjaTypeInvalid);
    }

    @Test
    @DisplayName("Deve listar todos os Personagens e retornar o Status Code 200")
    void shouldListAllCharactersAndReturnOkStatus(){
        when(characterService.listCharacter()).thenReturn(characterListResponseDTO);

        ResponseEntity<List<CharacterResponseDTO>> response = characterController.listCharacters();

        assertEquals(characterListResponseDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(characterService, times(1)).listCharacter();
    }

    @Test
    @DisplayName("Deve retornar um Ninja específico com status OK quando encontrado")
    void shouldReturnCharacterByIdAnd200OkStatus(){
        when(characterService.getCharacterById(VALID_CHARACTER_ID)).thenReturn(expectedCharacterJiraiya);

        ResponseEntity<CharacterResponseDTO> response = characterController.getCharacterById(VALID_CHARACTER_ID);

        assertEquals(expectedCharacterJiraiya, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(characterService, times(1)).getCharacterById(VALID_CHARACTER_ID);
    }

    @Test
    @DisplayName("Deve retornar uma exceção quando o ID do Personagem não for encontrado")
    void shouldThrowResourceNotFoundExceptionWhenCharacterIdIsNotFound() {
        String message = "Ninja with id " + INVALID_CHARACTER_ID + " not found.";
        when(characterService.getCharacterById(INVALID_CHARACTER_ID)).thenThrow(new ResourceNotFoundException(message));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            characterController.getCharacterById(INVALID_CHARACTER_ID);
        });

        assertEquals(message, exception.getMessage());
        verify(characterService, times(1)).getCharacterById(INVALID_CHARACTER_ID);
    }

    @Test
    @DisplayName("Deve listar todos os Personagens com o Ninja Type Informado e retornar o Status Code 200")
    void shouldListCharactersByNinjaTypeAndReturnOkStatus(){
        when(characterService.listCharactersByType(NINJA_TYPE_TAIJUTSU)).thenReturn(characterListResponseDTO);

        ResponseEntity<List<CharacterResponseDTO>> response = characterController.listCharactersByType(NINJA_TYPE_TAIJUTSU);

        assertEquals(characterListResponseDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @DisplayName("Deve deletar Personagem com sucesso e retornar 204 No Content")
    void shouldDeleteCharacterSuccessfullyAndReturn204NoContent(){
        doNothing().when(characterService).deleteCharacterById(VALID_CHARACTER_ID);

        ResponseEntity<Void> response = characterController.deleteCharacterById(VALID_CHARACTER_ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(characterService, times(1)).deleteCharacterById(VALID_CHARACTER_ID);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar deletar Personagem inexistente")
    void shouldThrowResourceNotFoundExceptionWhenDeletingNonExistentCharacter(){
        String message = "Ninja with id " + INVALID_CHARACTER_ID + " not found.";
        doThrow(new ResourceNotFoundException(message))
                .when(characterService).deleteCharacterById(INVALID_CHARACTER_ID);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            characterController.deleteCharacterById(INVALID_CHARACTER_ID);
        });

        assertEquals(message, exception.getMessage());
        verify(characterService, times(1)).deleteCharacterById(INVALID_CHARACTER_ID);
    }
    @Test
    @DisplayName("Deve adicionar o novo chakra e retornar sucesso")
    void shouldAddNewChakraAndReturnSuccess(){
        int chakraAmount = 100;

        when(characterService.addChakra(VALID_CHARACTER_ID, chakraAmount)).thenReturn(expectedCharacterJiraiya);

        ResponseEntity<CharacterResponseDTO> response = characterController.addChakra(VALID_CHARACTER_ID, chakraAmount);

        assertEquals(expectedCharacterJiraiya, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(characterService, times(1)).addChakra(VALID_CHARACTER_ID, chakraAmount);
    }


    @Test
    @DisplayName("Deve retornar Resultado da Batalha e Status 200 OK quando a Luta Ocorre.")
    void shouldReturnBattleResultAnd200OkStatusWhenFightOccurs(){
        when(characterService.fight(requestAttackDTO)).thenReturn(battleResponseDTO);

        ResponseEntity<BattleResponseDTO> response = characterController.fight(requestAttackDTO);

        assertEquals(battleResponseDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(characterService, times(1)).fight(requestAttackDTO);
    }
}
