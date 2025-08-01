package io.github.josiasbarreto_dev.desafio_naruto.mapper;

import io.github.josiasbarreto_dev.desafio_naruto.dto.JutsuRequestDTO;
import io.github.josiasbarreto_dev.desafio_naruto.dto.JutsuResponseDTO;
import io.github.josiasbarreto_dev.desafio_naruto.model.Jutsu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JutsuMapper {
    JutsuMapper INSTANCE = Mappers.getMapper(JutsuMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "character", ignore = true)
    Jutsu toEntity(JutsuRequestDTO dto);
    JutsuResponseDTO toDTO(Jutsu entity);
    List<JutsuResponseDTO> toDTO(List<Jutsu> entities);
}
