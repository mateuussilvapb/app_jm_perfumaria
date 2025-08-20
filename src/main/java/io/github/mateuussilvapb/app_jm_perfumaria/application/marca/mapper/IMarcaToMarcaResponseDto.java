package io.github.mateuussilvapb.app_jm_perfumaria.application.marca.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.dto.MarcaResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.marca.Marca;

public interface IMarcaToMarcaResponseDto {
    MarcaResponseDto toDto(Marca marca);
}
