package io.github.mateuussilvapb.app_jm_perfumaria.infra.marca.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.dto.MarcaResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.mapper.IMarcaToMarcaResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.marca.Marca;
import org.springframework.stereotype.Component;

@Component
public class MarcaToMarcaResponseDtoImpl implements IMarcaToMarcaResponseDto {
    @Override
    public MarcaResponseDto toDto(Marca marca) {
        return new MarcaResponseDto(
                marca.getId(),
                marca.getIdString(),
                marca.getNome(),
                marca.getStatus(),
                marca.getDescricao()
        );
    }
}
