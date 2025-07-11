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
    private CharacterResponseDTO payloadResponseDTO;
    private List<CharacterResponseDTO> characterListResponseDTO;
    private AttackRequestDTO requestAtackDTO;
    private BattleResponseDTO battleResponseDTO;

    private final Long VALID_CHARACTER_ID = 1L;
    private final Long INVALID_CHARACTER_ID = 10L;
    private final NinjaType NINJA_TYPE_TAIJUTSU = NinjaType.TAIJUTSU;


    @BeforeEach
    void setup() {
        payloadRequestDTO = new CharacterRequestDTO(
                "Jiraiya",
                Map.of(
                        "Rasengan", new JutsuRequestDTO(80, 60),
                        "Summoning Jutsu: Toad", new JutsuRequestDTO(20, 30),
                        "Sage Mode", new JutsuRequestDTO(0, 100)
                ),
                100,
                NinjaType.TAIJUTSU
        );

        payloadRequestWithNinjaTypeInvalid = new CharacterRequestDTO(
                "Jiraiya",
                Map.of(
                        "Rasengan", new JutsuRequestDTO(80, 60),
                        "Summoning Jutsu: Toad", new JutsuRequestDTO(20, 30),
                        "Sage Mode", new JutsuRequestDTO(0, 100)
                ),
                100,
                NinjaType.INVALID_TYPE
        );

        payloadResponseDTO = new CharacterResponseDTO(
                1L,
                "Jiraiya",
                Map.of(
                        "Rasengan", new Jutsu(80, 60),
                        "Summoning Jutsu: Toad", new Jutsu(20, 30),
                        "Sage Mode", new Jutsu(0, 100)
                ),
                100,
                100
                );

        characterListResponseDTO = List.of(
                new CharacterResponseDTO(
                        1L,
                        "Jiraiya",
                        Map.of(
                                "Rasengan", new Jutsu(80, 60),
                                "Summoning Jutsu: Toad", new Jutsu(20, 30),
                                "Sage Mode", new Jutsu(0, 100)
                        ),
                        100,
                        100
                ),
                new CharacterResponseDTO(
                        2L,
                        "Naruto Uzumaki",
                        Map.of(
                                "Shadow Clone Jutsu", new Jutsu(50, 40),
                                "Rasengan", new Jutsu(90, 70),
                                "Sage Mode", new Jutsu(0, 120)
                        ),
                        150,
                        200
                )
        );

        requestAtackDTO = new AttackRequestDTO(
                "Jiraiya",
                "Naruto Uzumaki",
                "Rasengan"
        );

        battleResponseDTO = new BattleResponseDTO(
                new CharacterResponseDTO(
                        1L,
                        "Jiraiya",
                        Map.of(
                                "Rasengan", new Jutsu(80, 60),
                                "Summoning Jutsu: Toad", new Jutsu(20, 30),
                                "Sage Mode", new Jutsu(0, 100)
                        ),
                        100,
                        100
                ),
                new CharacterResponseDTO(
                        2L,
                        "Naruto Uzumaki",
                        Map.of(
                                "Shadow Clone Jutsu", new Jutsu(50, 40),
                                "Rasengan", new Jutsu(90, 70),
                                "Sage Mode", new Jutsu(0, 120)
                        ),
                        150,
                        200
                )
        );
    }

    @Test
    @DisplayName("Deve criar um Personagem e retornar o Status Code 201")
    void shouldCreateCharacterSuccessfullyAndReturn201Created() {
        when(characterService.createCharacter(payloadRequestDTO)).thenReturn(payloadResponseDTO);
        ResponseEntity<CharacterResponseDTO> response = characterController.createCharacter(payloadRequestDTO);

        assertNotNull(response);
        assertEquals(payloadResponseDTO, response.getBody());
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

        ResponseEntity<List<CharacterResponseDTO>> response = characterController.listCharacter();

        assertNotNull(response);
        assertEquals(characterListResponseDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(characterService, times(1)).listCharacter();
    }

    @Test
    @DisplayName("Deve retornar um Ninja específico com status OK quando encontrado")
    void shouldReturnCharacterByIdAnd200OkStatus(){
        when(characterService.getCharacterById(VALID_CHARACTER_ID)).thenReturn(payloadResponseDTO);

        ResponseEntity<CharacterResponseDTO> response = characterController.getCharacterById(VALID_CHARACTER_ID);

        assertNotNull(response);
        assertEquals(payloadResponseDTO, response.getBody());
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

        assertNotNull(response);
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

        when(characterService.addChakra(VALID_CHARACTER_ID, chakraAmount)).thenReturn(payloadResponseDTO);

        ResponseEntity<CharacterResponseDTO> response = characterController.addChakra(VALID_CHARACTER_ID, chakraAmount);

        assertNotNull(response);
        assertEquals(payloadResponseDTO, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(characterService, times(1)).addChakra(VALID_CHARACTER_ID, chakraAmount);
    }


    @Test
    @DisplayName("Deve retornar Resultado da Batalha e Status 200 OK quando a Luta Ocorre.")
    void shouldReturnBattleResultAnd200OkStatusWhenFightOccurs(){
        when(characterService.fight(requestAtackDTO)).thenReturn(battleResponseDTO);

        ResponseEntity<BattleResponseDTO> response = characterController.fight(requestAtackDTO);

        assertNotNull(response);
        assertEquals(battleResponseDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(characterService, times(1)).fight(requestAtackDTO);
    }

}
