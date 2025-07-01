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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CharacterService Unit Tests")
public class CharacterServiceTest {

    @InjectMocks
    CharacterService characterService;

    @Mock
    GenjutsuRepository genjutsuRepository;
    @Mock
    NinjutsuRepository ninjutsuRepository;
    @Mock
    TaijutsuRepository taijutsuRepository;
    @Mock
    CharacterMapper mapper;

    private CharacterRequestDTO taijutsuCharacterRequest;
    private CharacterRequestDTO ninjutsuCharacterRequest;
    private CharacterRequestDTO genjutsuCharacterRequest;
    private JutsuRequestDTO jutsuRequestDTO;
    private ChakraRequestDTO chakraRequestDTO;

    private TaijutsuNinja taijutsuNinja;
    private NinjutsuNinja ninjutsuNinja;
    private GenjutsuNinja genjutsuNinja;

    private CharacterResponseDTO taijutsuResponseDTO;
    private CharacterResponseDTO ninjutsuResponseDTO;
    private CharacterResponseDTO genjutsuResponseDTO;

    private final Long VALID_CHARACTER_ID = 1L;
    private final Long INVALID_CHARACTER_ID = 99L;

    @BeforeEach
    void setup() {
        taijutsuCharacterRequest = new CharacterRequestDTO(
                "Rock Lee", 17, "Konoha", new ArrayList<>(Arrays.asList("Leaf Hurricane", "Primary Lotus")),
                50, NinjaType.TAIJUTSU
        );
        ninjutsuCharacterRequest = new CharacterRequestDTO(
                "Naruto Uzumaki", 17, "Konoha", new ArrayList<>(Arrays.asList("Rasengan", "Shadow Clone Jutsu")),
                1000, NinjaType.NINJUTSU
        );
        genjutsuCharacterRequest = new CharacterRequestDTO(
                "Itachi Uchiha", 21, "Konoha", new ArrayList<>(Arrays.asList("Tsukuyomi", "Izanami")),
                800, NinjaType.GENJUTSU
        );

        jutsuRequestDTO = new JutsuRequestDTO("New Jutsu", NinjaType.TAIJUTSU);
        chakraRequestDTO = new ChakraRequestDTO(200, NinjaType.NINJUTSU);

        taijutsuNinja = new TaijutsuNinja(
                VALID_CHARACTER_ID, "Rock Lee", 17, "Konoha", new ArrayList<>(Arrays.asList("Leaf Hurricane", "Primary Lotus")),
                50, NinjaType.TAIJUTSU
        );
        ninjutsuNinja = new NinjutsuNinja(
                VALID_CHARACTER_ID, "Naruto Uzumaki", 17, "Konoha", new ArrayList<>(Arrays.asList("Rasengan", "Shadow Clone Jutsu")),
                1000, NinjaType.NINJUTSU
        );
        genjutsuNinja = new GenjutsuNinja(
                VALID_CHARACTER_ID, "Itachi Uchiha", 21, "Konoha", new ArrayList<>(Arrays.asList("Tsukuyomi", "Izanami")),
                800, NinjaType.GENJUTSU
        );

        taijutsuResponseDTO = new CharacterResponseDTO(
                VALID_CHARACTER_ID, "Rock Lee", 17, "Konoha", new ArrayList<>(Arrays.asList("Leaf Hurricane", "Primary Lotus")),
                50, NinjaType.TAIJUTSU
        );
        ninjutsuResponseDTO = new CharacterResponseDTO(
                VALID_CHARACTER_ID, "Naruto Uzumaki", 17, "Konoha", new ArrayList<>(Arrays.asList("Rasengan", "Shadow Clone Jutsu")),
                1000, NinjaType.NINJUTSU
        );
        genjutsuResponseDTO = new CharacterResponseDTO(
                VALID_CHARACTER_ID, "Itachi Uchiha", 21, "Konoha", new ArrayList<>(Arrays.asList("Tsukuyomi", "Izanami")),
                800, NinjaType.GENJUTSU
        );
    }

    @Test
    @DisplayName("Deve criar um Personagem Taijutsu com Sucesso")
    void shouldCreateTaijutsuCharacterSuccessfully() {
        when(mapper.toEntityTaijutsu(taijutsuCharacterRequest)).thenReturn(taijutsuNinja);
        when(taijutsuRepository.save(taijutsuNinja)).thenReturn(taijutsuNinja);
        when(mapper.toDTO(taijutsuNinja)).thenReturn(taijutsuResponseDTO);

        CharacterResponseDTO response = characterService.createCharacter(taijutsuCharacterRequest);

        assertNotNull(response);
        assertEquals(taijutsuResponseDTO.id(), response.id());
        assertEquals(taijutsuResponseDTO.name(), response.name());
        assertEquals(taijutsuResponseDTO.ninjaType(), response.ninjaType());

        verify(mapper, times(1)).toEntityTaijutsu(taijutsuCharacterRequest);
        verify(taijutsuRepository, times(1)).save(taijutsuNinja);
        verify(mapper, times(1)).toDTO(taijutsuNinja);
        verifyNoMoreInteractions(taijutsuRepository, ninjutsuRepository, genjutsuRepository, mapper);
    }

    @Test
    @DisplayName("Deve criar um Personagem Ninjutsu com Sucesso")
    void shouldCreateNinjutsuCharacterSuccessfully() {
        when(mapper.toEntityNinjutsu(ninjutsuCharacterRequest)).thenReturn(ninjutsuNinja);
        when(ninjutsuRepository.save(ninjutsuNinja)).thenReturn(ninjutsuNinja);
        when(mapper.toDTO(ninjutsuNinja)).thenReturn(ninjutsuResponseDTO);

        CharacterResponseDTO response = characterService.createCharacter(ninjutsuCharacterRequest);

        assertNotNull(response);
        assertEquals(ninjutsuResponseDTO.id(), response.id());
        assertEquals(ninjutsuResponseDTO.name(), response.name());
        assertEquals(ninjutsuResponseDTO.ninjaType(), response.ninjaType());

        verify(mapper, times(1)).toEntityNinjutsu(ninjutsuCharacterRequest);
        verify(ninjutsuRepository, times(1)).save(ninjutsuNinja);
        verify(mapper, times(1)).toDTO(ninjutsuNinja);
        verifyNoMoreInteractions(taijutsuRepository, ninjutsuRepository, genjutsuRepository, mapper);
    }

    @Test
    @DisplayName("Deve criar um Personagem Genjutsu com Sucesso")
    void shouldCreateGenjutsuCharacterSuccessfully() {
        when(mapper.toEntityGenjutsu(genjutsuCharacterRequest)).thenReturn(genjutsuNinja);
        when(genjutsuRepository.save(genjutsuNinja)).thenReturn(genjutsuNinja);
        when(mapper.toDTO(genjutsuNinja)).thenReturn(genjutsuResponseDTO);

        CharacterResponseDTO response = characterService.createCharacter(genjutsuCharacterRequest);

        assertNotNull(response);
        assertEquals(genjutsuResponseDTO.id(), response.id());
        assertEquals(genjutsuResponseDTO.name(), response.name());
        assertEquals(genjutsuResponseDTO.ninjaType(), response.ninjaType());

        verify(mapper, times(1)).toEntityGenjutsu(genjutsuCharacterRequest);
        verify(genjutsuRepository, times(1)).save(genjutsuNinja);
        verify(mapper, times(1)).toDTO(genjutsuNinja);
        verifyNoMoreInteractions(taijutsuRepository, ninjutsuRepository, genjutsuRepository, mapper);
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar Personagem com NinjaType inválido/não suportado")
    void shouldThrowExceptionWhenCreatingCharacterWithInvalidNinjaType() {
        CharacterRequestDTO invalidRequest = new CharacterRequestDTO(
                "Invalid Ninja",
                10,
                "Hidden Village",
                new ArrayList<>(),
                100,
                NinjaType.INVALID_TYPE
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                characterService.createCharacter(invalidRequest)
        );

        assertEquals("Ninja type is invalid", exception.getMessage());
        verifyNoInteractions(genjutsuRepository, ninjutsuRepository, taijutsuRepository, mapper);
    }

    @Test
    @DisplayName("Deve adicionar um Jutsu a um Personagem Taijutsu com Sucesso")
    void shouldAddNewJutsuToTaijutsuCharacterSuccessfully() {
        TaijutsuNinja taijutsuNinjaToModify = new TaijutsuNinja(
                taijutsuNinja.getId(), taijutsuNinja.getName(), taijutsuNinja.getAge(),
                taijutsuNinja.getVillage(), new ArrayList<>(taijutsuNinja.getJutsus()),
                taijutsuNinja.getChakra(), taijutsuNinja.getNinjaType()
        );

        when(taijutsuRepository.findById(VALID_CHARACTER_ID)).thenReturn(Optional.of(taijutsuNinjaToModify));

        taijutsuNinjaToModify.addJutsu(jutsuRequestDTO.jutsuName());

        CharacterResponseDTO expectedResponse = new CharacterResponseDTO(
                taijutsuNinjaToModify.getId(), taijutsuNinjaToModify.getName(), taijutsuNinjaToModify.getAge(),
                taijutsuNinjaToModify.getVillage(), taijutsuNinjaToModify.getJutsus(),
                taijutsuNinjaToModify.getChakra(), taijutsuNinjaToModify.getNinjaType()
        );

        when(taijutsuRepository.save(taijutsuNinjaToModify)).thenReturn(taijutsuNinjaToModify);
        when(mapper.toDTO(taijutsuNinjaToModify)).thenReturn(expectedResponse);

        CharacterResponseDTO response = characterService.addNewJutsu(VALID_CHARACTER_ID, jutsuRequestDTO);

        assertNotNull(response);
        assertTrue(response.jutsus().contains(jutsuRequestDTO.jutsuName()));
        assertEquals(expectedResponse.jutsus().size(), response.jutsus().size());

        verify(taijutsuRepository, times(1)).findById(VALID_CHARACTER_ID);
        verify(taijutsuRepository, times(1)).save(taijutsuNinjaToModify);
        verify(mapper, times(1)).toDTO(taijutsuNinjaToModify);
        verifyNoMoreInteractions(taijutsuRepository, ninjutsuRepository, genjutsuRepository, mapper);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao adicionar Jutsu para ID inexistente")
    void shouldThrowResourceNotFoundExceptionWhenAddNewJutsuToInvalidId() {
        when(taijutsuRepository.findById(INVALID_CHARACTER_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                characterService.addNewJutsu(INVALID_CHARACTER_ID, jutsuRequestDTO)
        );
        assertEquals("Character with ID " + INVALID_CHARACTER_ID + " not found", exception.getMessage());

        verify(taijutsuRepository, times(1)).findById(INVALID_CHARACTER_ID);
        verifyNoMoreInteractions(taijutsuRepository, ninjutsuRepository, genjutsuRepository, mapper);
    }

    @Test
    @DisplayName("Deve lançar exceção ao adicionar Jutsu com NinjaType INVALID_TYPE")
    void shouldThrowExceptionWhenAddNewJutsuWithInvalidNinjaType() {
        JutsuRequestDTO invalidJutsuRequest = new JutsuRequestDTO(
                "Invalid Jutsu",
                NinjaType.INVALID_TYPE
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                characterService.addNewJutsu(VALID_CHARACTER_ID, invalidJutsuRequest)
        );
        assertEquals("Ninja type is invalid", exception.getMessage());

        verifyNoInteractions(genjutsuRepository, ninjutsuRepository, taijutsuRepository, mapper);
    }

    @Test
    @DisplayName("Deve aumentar o Chakra de um Personagem Ninjutsu com Sucesso")
    void shouldIncreaseChakraOfNinjutsuCharacterSuccessfully() {
        NinjutsuNinja ninjutsuNinjaToModify = new NinjutsuNinja(
                ninjutsuNinja.getId(), ninjutsuNinja.getName(), ninjutsuNinja.getAge(),
                ninjutsuNinja.getVillage(), new ArrayList<>(ninjutsuNinja.getJutsus()),
                ninjutsuNinja.getChakra(), ninjutsuNinja.getNinjaType()
        );

        when(ninjutsuRepository.findById(VALID_CHARACTER_ID)).thenReturn(Optional.of(ninjutsuNinjaToModify));

        ninjutsuNinjaToModify.increaseChakra(chakraRequestDTO.chakraValue());

        CharacterResponseDTO expectedResponse = new CharacterResponseDTO(
                ninjutsuNinjaToModify.getId(), ninjutsuNinjaToModify.getName(), ninjutsuNinjaToModify.getAge(),
                ninjutsuNinjaToModify.getVillage(), ninjutsuNinjaToModify.getJutsus(),
                ninjutsuNinjaToModify.getChakra(), ninjutsuNinjaToModify.getNinjaType()
        );

        when(ninjutsuRepository.save(ninjutsuNinjaToModify)).thenReturn(ninjutsuNinjaToModify);
        when(mapper.toDTO(ninjutsuNinjaToModify)).thenReturn(expectedResponse);

        CharacterResponseDTO response = characterService.increaseChakra(VALID_CHARACTER_ID, chakraRequestDTO);

        assertNotNull(response);
        assertEquals(expectedResponse.chakra(), response.chakra());

        verify(ninjutsuRepository, times(1)).findById(VALID_CHARACTER_ID);
        verify(ninjutsuRepository, times(1)).save(ninjutsuNinjaToModify);
        verify(mapper, times(1)).toDTO(ninjutsuNinjaToModify);
        verifyNoMoreInteractions(taijutsuRepository, ninjutsuRepository, genjutsuRepository, mapper);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao aumentar Chakra para ID inexistente")
    void shouldThrowResourceNotFoundExceptionWhenIncreaseChakraToInvalidId() {
        when(ninjutsuRepository.findById(INVALID_CHARACTER_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                characterService.increaseChakra(INVALID_CHARACTER_ID, chakraRequestDTO)
        );
        assertEquals("Character with ID " + INVALID_CHARACTER_ID + " not found", exception.getMessage());

        verify(ninjutsuRepository, times(1)).findById(INVALID_CHARACTER_ID);
        verifyNoMoreInteractions(taijutsuRepository, ninjutsuRepository, genjutsuRepository, mapper);
    }

    @Test
    @DisplayName("Deve lançar exceção ao aumentar Chakra com NinjaType INVALID_TYPE")
    void shouldThrowExceptionWhenIncreaseChakraWithInvalidNinjaType() {
        ChakraRequestDTO invalidChakraRequest = new ChakraRequestDTO(
                100,
                NinjaType.INVALID_TYPE
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                characterService.increaseChakra(VALID_CHARACTER_ID, invalidChakraRequest)
        );
        assertEquals("Ninja type is invalid", exception.getMessage());

        verifyNoInteractions(genjutsuRepository, ninjutsuRepository, taijutsuRepository, mapper);
    }

    @Test
    @DisplayName("Deve retornar as informações de exibição de um Personagem Genjutsu")
    void shouldReturnDisplayInfoOfGenjutsuCharacter() {
        GenjutsuNinja mockGenjutsuNinja = mock(GenjutsuNinja.class);

        when(genjutsuRepository.findById(VALID_CHARACTER_ID)).thenReturn(Optional.of(mockGenjutsuNinja));
        when(mockGenjutsuNinja.displayInfo()).thenReturn("Display Info for Itachi Uchiha");

        String displayInfo = characterService.getDisplayInfo(VALID_CHARACTER_ID, NinjaType.GENJUTSU);

        assertNotNull(displayInfo);
        assertEquals("Display Info for Itachi Uchiha", displayInfo);

        verify(genjutsuRepository, times(1)).findById(VALID_CHARACTER_ID);
        verify(mockGenjutsuNinja, times(1)).displayInfo();
        verifyNoMoreInteractions(genjutsuRepository, ninjutsuRepository, taijutsuRepository, mapper, mockGenjutsuNinja);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao obter Display Info para ID inexistente")
    void shouldThrowResourceNotFoundExceptionWhenGetDisplayInfoForInvalidId() {
        when(genjutsuRepository.findById(INVALID_CHARACTER_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                characterService.getDisplayInfo(INVALID_CHARACTER_ID, NinjaType.GENJUTSU)
        );
        assertEquals("Character with ID " + INVALID_CHARACTER_ID + " not found", exception.getMessage());

        verify(genjutsuRepository, times(1)).findById(INVALID_CHARACTER_ID);
        verifyNoMoreInteractions(genjutsuRepository, ninjutsuRepository, genjutsuRepository, mapper);
    }

    @Test
    @DisplayName("Deve lançar exceção ao obter Display Info com NinjaType INVALID_TYPE")
    void shouldThrowExceptionWhenGetDisplayInfoWithInvalidNinjaType() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                characterService.getDisplayInfo(VALID_CHARACTER_ID, NinjaType.INVALID_TYPE)
        );
        assertEquals("Ninja type is invalid", exception.getMessage());

        verifyNoInteractions(genjutsuRepository, ninjutsuRepository, taijutsuRepository, mapper);
    }

    @Test
    @DisplayName("Deve retornar o uso de Jutsu de um Personagem Ninjutsu")
    void shouldReturnUseJutsuOfNinjutsuCharacter() {
        NinjutsuNinja mockNinjutsuNinja = mock(NinjutsuNinja.class);

        when(ninjutsuRepository.findById(VALID_CHARACTER_ID)).thenReturn(Optional.of(mockNinjutsuNinja));
        when(mockNinjutsuNinja.useJutsu()).thenReturn("Naruto used Rasengan!");

        String useJutsuResult = characterService.useJutsu(VALID_CHARACTER_ID, NinjaType.NINJUTSU);

        assertNotNull(useJutsuResult);
        assertEquals("Naruto used Rasengan!", useJutsuResult);

        verify(ninjutsuRepository, times(1)).findById(VALID_CHARACTER_ID);
        verify(mockNinjutsuNinja, times(1)).useJutsu();
        verifyNoMoreInteractions(genjutsuRepository, ninjutsuRepository, taijutsuRepository, mapper, mockNinjutsuNinja);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao usar Jutsu para ID inexistente")
    void shouldThrowResourceNotFoundExceptionWhenUseJutsuForInvalidId() {
        when(ninjutsuRepository.findById(INVALID_CHARACTER_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                characterService.useJutsu(INVALID_CHARACTER_ID, NinjaType.NINJUTSU)
        );
        assertEquals("Character with ID " + INVALID_CHARACTER_ID + " not found", exception.getMessage());

        verify(ninjutsuRepository, times(1)).findById(INVALID_CHARACTER_ID);
        verifyNoMoreInteractions(taijutsuRepository, ninjutsuRepository, genjutsuRepository, mapper);
    }

    @Test
    @DisplayName("Deve lançar exceção ao usar Jutsu com NinjaType INVALID_TYPE")
    void shouldThrowExceptionWhenUseJutsuWithInvalidNinjaType() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                characterService.useJutsu(VALID_CHARACTER_ID, NinjaType.INVALID_TYPE)
        );
        assertEquals("Ninja type is invalid", exception.getMessage());

        verifyNoInteractions(genjutsuRepository, ninjutsuRepository, taijutsuRepository, mapper);
    }

    @Test
    @DisplayName("Deve retornar o resultado da esquiva de um Personagem Taijutsu")
    void shouldReturnDodgeCharacterOfTaijutsuCharacter() {
        TaijutsuNinja mockTaijutsuNinja = mock(TaijutsuNinja.class);

        when(taijutsuRepository.findById(VALID_CHARACTER_ID)).thenReturn(Optional.of(mockTaijutsuNinja));
        when(mockTaijutsuNinja.dodge()).thenReturn("Rock Lee dodged the attack with incredible speed!");

        String dodgeResult = characterService.dodgeCharacter(VALID_CHARACTER_ID, NinjaType.TAIJUTSU);

        assertNotNull(dodgeResult);
        assertEquals("Rock Lee dodged the attack with incredible speed!", dodgeResult);

        verify(taijutsuRepository, times(1)).findById(VALID_CHARACTER_ID);
        verify(mockTaijutsuNinja, times(1)).dodge();
        verifyNoMoreInteractions(genjutsuRepository, ninjutsuRepository, taijutsuRepository, mapper, mockTaijutsuNinja);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao esquivar para ID inexistente")
    void shouldThrowResourceNotFoundExceptionWhenDodgeCharacterForInvalidId() {
        when(taijutsuRepository.findById(INVALID_CHARACTER_ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                characterService.dodgeCharacter(INVALID_CHARACTER_ID, NinjaType.TAIJUTSU)
        );
        assertEquals("Character with ID " + INVALID_CHARACTER_ID + " not found", exception.getMessage());

        verify(taijutsuRepository, times(1)).findById(INVALID_CHARACTER_ID);
        verifyNoMoreInteractions(taijutsuRepository, ninjutsuRepository, genjutsuRepository, mapper);
    }

    @Test
    @DisplayName("Deve lançar exceção ao esquivar com NinjaType INVALID_TYPE")
    void shouldThrowExceptionWhenDodgeCharacterWithInvalidNinjaType() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                characterService.dodgeCharacter(VALID_CHARACTER_ID, NinjaType.INVALID_TYPE)
        );
        assertEquals("Ninja type is invalid", exception.getMessage());

        verifyNoInteractions(genjutsuRepository, ninjutsuRepository, taijutsuRepository, mapper);
    }
}