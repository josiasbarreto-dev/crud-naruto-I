package io.github.josiasbarreto_dev.desafio_naruto.controller;

import io.github.josiasbarreto_dev.desafio_naruto.dto.ChakraRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.JutsuRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import io.github.josiasbarreto_dev.desafio_naruto.service.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Controller Unit Tests")
public class CharacterControllerTest {
    @InjectMocks
    private CharacterController characterController;

    @Mock
    private CharacterService characterService;

    private ChakraRequestDTO payloadChakraRequestDTO;
    private CharacterRequestDTO payloadRequestDTO;
    private CharacterRequestDTO payloadRequestWithNinjaTypeInvalid;
    private JutsuRequestDTO payloadJutsuRequestDTO;
    private CharacterResponseDTO payloadResponseDTO;
    private CharacterResponseDTO payloadJutsuResponseDTO;
    private CharacterResponseDTO payloadChakraResponseDTO;

    private final Long VALID_CHARACTER_ID = 1L;
    private final NinjaType NINJA_TYPE = NinjaType.TAIJUTSU;


    @BeforeEach
    void setup() {
        payloadRequestDTO = new CharacterRequestDTO(
                "Naruto Uzumaki",
                17,
                "Konoha",
                new ArrayList<>(Arrays.asList(
                        "Shadow Clone Jutsu",
                        "Rasengan",
                        "Sage Mode",
                        "Kurama Chakra Mode"
                )),
                1000,
                NinjaType.TAIJUTSU
        );

        payloadRequestWithNinjaTypeInvalid = new CharacterRequestDTO(
                "Naruto Uzumaki",
                17,
                "Konoha",
                new ArrayList<>(Arrays.asList(
                        "Shadow Clone Jutsu",
                        "Rasengan",
                        "Sage Mode",
                        "Kurama Chakra Mode"
                )),
                1000,
                NinjaType.TAIJUTSU
        );

        payloadResponseDTO = new CharacterResponseDTO(
                1L,
                "Naruto Uzumaki",
                17,
                "Konoha",
                new ArrayList<>(Arrays.asList(
                        "Shadow Clone Jutsu",
                        "Rasengan",
                        "Sage Mode",
                        "Kurama Chakra Mode"
                )),
                1000,
                NinjaType.TAIJUTSU
        );

        payloadJutsuRequestDTO = new JutsuRequestDTO(
                "Shouton",
                NinjaType.TAIJUTSU
        );

        payloadChakraRequestDTO = new ChakraRequestDTO(
                500,
                NinjaType.NINJUTSU
        );


        payloadJutsuResponseDTO = new CharacterResponseDTO(
                1L,
                "Naruto Uzumaki",
                17,
                "Konoha",
                new ArrayList<>(Arrays.asList(
                        "Shadow Clone Jutsu",
                        "Rasengan",
                        "Sage Mode",
                        "Kurama Chakra Mode",
                        "Shouton"
                )),
                1000,
                NinjaType.TAIJUTSU
        );

        payloadChakraResponseDTO = new CharacterResponseDTO(
                1L,
                "Naruto Uzumaki",
                17,
                "Konoha",
                new ArrayList<>(Arrays.asList(
                        "Shadow Clone Jutsu",
                        "Rasengan",
                        "Sage Mode",
                        "Kurama Chakra Mode"
                )),
                1500,
                NinjaType.NINJUTSU
        );
    }

    @Test
    @DisplayName("Deve criar um Personagem e retornar o Status Code 201")
    void shouldCreateCharacterAndReturnSuccess() {
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

        Exception exception = assertThrows(RuntimeException.class, () -> {
            characterController.createCharacter(payloadRequestWithNinjaTypeInvalid);
        });

        assertEquals(message, exception.getMessage());
        verify(characterService, times(1)).createCharacter(payloadRequestWithNinjaTypeInvalid);
    }

    @Test
    @DisplayName("Deve adicionar novo jutsu ao array de jutsus e retornar sucesso")
    void shouldAddNewJutsuToJutsuArrayAndReturnSuccess(){
        when(characterService.addNewJutsu(VALID_CHARACTER_ID, payloadJutsuRequestDTO)).thenReturn(payloadJutsuResponseDTO);

        ResponseEntity<CharacterResponseDTO> response = characterController.addNewJutsu(VALID_CHARACTER_ID, payloadJutsuRequestDTO);

        assertNotNull(response);
        assertEquals(payloadJutsuResponseDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(characterService, times(1)).addNewJutsu(VALID_CHARACTER_ID, payloadJutsuRequestDTO);
    }

    @Test
    @DisplayName("Deve adicionar o novo chakra e retornar sucesso")
    void shouldAddNewChakraAndReturnSuccess(){
        when(characterService.increaseChakra(VALID_CHARACTER_ID, payloadChakraRequestDTO)).thenReturn(payloadChakraResponseDTO);

        ResponseEntity<CharacterResponseDTO> response = characterController.increaseChakra(VALID_CHARACTER_ID, payloadChakraRequestDTO);

        assertNotNull(response);
        assertEquals(payloadChakraResponseDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(characterService, times(1)).increaseChakra(VALID_CHARACTER_ID, payloadChakraRequestDTO);
    }

    @Test
    @DisplayName("Deve retornar as informações do personagem e status 200 OK")
    void shouldReturnCharacterInfoAndOkStatus(){
        String expectedContent = "Character{id=1, name='Might Guy', age=30, village='Konohagakure', jutsus=[Eight Inner Gates, Chidori], chakra=1700, ninjaType=TAIJUTSU}";

        when(characterService.getDisplayInfo(VALID_CHARACTER_ID, NINJA_TYPE)).thenReturn(expectedContent);

        ResponseEntity<String> response = characterController.getDisplayInfo(VALID_CHARACTER_ID, NINJA_TYPE);

        assertNotNull(response);
        assertEquals(expectedContent, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(characterService, times(1)).getDisplayInfo(VALID_CHARACTER_ID, NINJA_TYPE);
    }

    @Test
    @DisplayName("Deve exibir uma mensagem indicando que o personagem está usando um jutsu")
    void shouldDisplayMessageWhenUsingJutsu(){
        String expectedContent = "The character Might Guy unleashes a Taijutsu attack!";

        when(characterService.useJutsu(VALID_CHARACTER_ID, NINJA_TYPE)).thenReturn(expectedContent);

        ResponseEntity<String> response = characterController.useJutsuCharacter(VALID_CHARACTER_ID, NINJA_TYPE);

        assertNotNull(response);
        assertEquals(expectedContent, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(characterService, times(1)).useJutsu(VALID_CHARACTER_ID, NINJA_TYPE);
    }

    @Test
    @DisplayName("Deve exibir mensagem de desvio")
    void shouldDisplayDodgeMessage(){
        String expectedContent = "The attack was dodged using Taijutsu!";

        when(characterService.dodgeCharacter(VALID_CHARACTER_ID, NINJA_TYPE)).thenReturn(expectedContent);

        ResponseEntity<String> response = characterController.dodgeCharacter(VALID_CHARACTER_ID, NINJA_TYPE);

        assertNotNull(response);
        assertEquals(expectedContent, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(characterService, times(1)).dodgeCharacter(VALID_CHARACTER_ID, NINJA_TYPE);
    }

}
