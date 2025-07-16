package io.github.josiasbarreto_dev.desafio_naruto.controller;

import io.github.josiasbarreto_dev.desafio_naruto.dto.AttackRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.BattleResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/character")
@Tag(name = "API Naruto Character Battle", description = "Endpoints for managing characters in the Naruto universe")
public interface CharacterControllerInterface {

    @Operation(summary = "Create a new character", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Character created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Character with this name already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CharacterResponseDTO> createCharacter(
            @Parameter(description = "Character data to create", required = true, schema = @Schema(implementation = CharacterRequestDTO.class))
            @RequestBody CharacterRequestDTO requestDTO
    );

    @Operation(summary = "List all characters", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of characters retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CharacterResponseDTO>> listCharacters();

    @Operation(summary = "Get character by ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character found"),
            @ApiResponse(responseCode = "404", description = "Character not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @GetMapping(value = "/{characterId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CharacterResponseDTO> getCharacterById(
            @Parameter(description = "ID of the character to retrieve", required = true, schema = @Schema(type = "integer", format = "int64", example = "1"))
            @PathVariable Long characterId
    );

    @Operation(summary = "List characters by type", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of characters by type retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ninja type"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @GetMapping(value = "/type", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CharacterResponseDTO>> listCharactersByType(
            @Parameter(description = "Type of ninja to filter by (NINJUTSU, TAIJUTSU)", required = true, schema = @Schema(implementation = NinjaType.class))
            @RequestParam NinjaType ninjaType
    );

    @Operation(summary = "Delete character by ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Character deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Character not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @DeleteMapping(value = "/{characterId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteCharacterById(
            @Parameter(description = "ID of the character to delete", required = true, schema = @Schema(type = "integer", format = "int64", example = "1"))
            @PathVariable Long characterId);

    @Operation(summary = "Fight between characters", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Battle result returned successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid attack request data"),
            @ApiResponse(responseCode = "404", description = "One or both characters not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @PostMapping(value = "/battle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BattleResponseDTO> fight(@RequestBody AttackRequestDTO attackRequestDTO);

    @Operation(summary = "Add chakra to a ninja", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chakra added successfully"),
            @ApiResponse(responseCode = "404", description = "Ninja not found"),
            @ApiResponse(responseCode = "400", description = "Invalid chakra amount"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @PostMapping(value = "/chakra/{ninjaId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CharacterResponseDTO> addChakra(
            @Parameter(description = "ID of the ninja to add chakra to", required = true, schema = @Schema(type = "integer", format = "int64", example = "1"))
            @PathVariable Long ninjaId,
            @Parameter(description = "Amount of chakra to add", required = true, schema = @Schema(type = "integer", example = "50"))
            @RequestParam int chakraAmount
    );
}