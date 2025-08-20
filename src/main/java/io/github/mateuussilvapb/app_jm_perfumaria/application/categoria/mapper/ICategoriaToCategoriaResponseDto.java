package io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.dto.CategoriaResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.categoria.Categoria;

public interface ICategoriaToCategoriaResponseDto {
    CategoriaResponseDto toDto(Categoria marca);
}
