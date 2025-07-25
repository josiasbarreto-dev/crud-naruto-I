package io.github.josiasbarreto_dev.desafio_naruto.mapper;

import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {JutsuMapper.class})
public interface CharacterMapper {
    CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);

    CharacterResponseDTO toDTO(Character character);
    List<CharacterResponseDTO> toDTOCharacters(List<Character> characters);
}
