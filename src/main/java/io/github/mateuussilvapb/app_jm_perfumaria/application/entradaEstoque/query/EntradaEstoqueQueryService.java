package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.query;

import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.dto.EntradaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper.IEntradaEstoqueToEntradaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.entradaEstoque.repository.IEntradaEstoqueRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EntradaEstoqueQueryService {

    private final IEntradaEstoqueRepository entradaEstoqueRepository;
    private final IEntradaEstoqueToEntradaEstoqueResponseDto entradaEstoqueMapper;

    public List<EntradaEstoqueResponseDto> findAll() {
        return this.entradaEstoqueRepository.findAll().stream().map(entradaEstoqueMapper::toDto).toList();
    }
}
