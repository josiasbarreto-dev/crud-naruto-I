package io.github.josiasbarreto_dev.desafio_naruto.mapper;

import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.JutsuRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.model.Character;
import io.github.josiasbarreto_dev.desafio_naruto.model.Jutsu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CharacterMapper {
    CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);

    CharacterResponseDTO toDTO(Character character);
    List<CharacterResponseDTO> toDTO(List<Character> characters);

    Jutsu toEntity(JutsuRequestDTO jutsuRequestDTO);
    JutsuRequestDTO toDTO(Jutsu jutsu);
}
