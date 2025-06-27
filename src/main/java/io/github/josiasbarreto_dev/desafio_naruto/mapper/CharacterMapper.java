package io.github.josiasbarreto_dev.desafio_naruto.mapper;

import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.CharacterResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.model.GenjutsuNinja;
import io.github.josiasbarreto_dev.desafio_naruto.model.NinjutsuNinja;
import io.github.josiasbarreto_dev.desafio_naruto.model.TaijutsuNinja;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CharacterMapper {
    CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);

    @Mapping(target = "id", ignore = true)
    GenjutsuNinja toEntityGenjutsu(CharacterRequestDTO requestDTO);
    @Mapping(target = "id", ignore = true)
    NinjutsuNinja toEntityNinjutsu(CharacterRequestDTO requestDTO);
    @Mapping(target = "id", ignore = true)
    TaijutsuNinja toEntityTaijutsu(CharacterRequestDTO requestDTO);

    CharacterResponseDTO toDTO(GenjutsuNinja entityGenjutsu);
    CharacterResponseDTO toDTO(NinjutsuNinja entityNinjutsu);
    CharacterResponseDTO toDTO(TaijutsuNinja entityTaijutsu);
}
