package io.github.mateuussilvapb.app_jm_perfumaria.infra.categoria.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.dto.CategoriaResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.mapper.ICategoriaToCategoriaResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.categoria.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaToCategoriaResponseDtoImpl implements ICategoriaToCategoriaResponseDto {
    @Override
    public CategoriaResponseDto toDto(Categoria categoria) {
        return new CategoriaResponseDto(
                categoria.getId(),
                categoria.getIdString(),
                categoria.getNome(),
                categoria.getStatus(),
                categoria.getDescricao()
        );
    }
}
