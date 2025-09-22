package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.query;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueToViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueFilterDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.exceptions.EntradaEstoqueNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper.IEntradaEstoqueToEntradaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper.IEntradaEstoqueToEntradaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.mapper.IEntradaEstoqueToEntradaEstoqueViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.query.specification.EntradaEstoqueSpecifications;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
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
    private final IEntradaEstoqueToEntradaEstoqueResponseListDto entradaEstoqueListMapper;
    private final IEntradaEstoqueToEntradaEstoqueViewUpdateResponseDto entradaEstoqueToViewUpdateMapper;

    public List<MovimentacaoEstoqueResponseDto> findAll() {
        return this.entradaEstoqueRepository.findAll().stream().map(entradaEstoqueMapper::toDto).toList();
    }

    public List<MovimentacaoEstoqueResponseListDto> findAllToList() {
        return this.entradaEstoqueRepository.findAll().stream().map(entradaEstoqueListMapper::toDto).toList();
    }

    public MovimentacaoEstoqueToViewUpdateResponseDto findById(Long id) {
        var entradaEstoque = this.entradaEstoqueRepository.findById(id);
        if (entradaEstoque.isPresent()) {
            return entradaEstoqueToViewUpdateMapper.toDto(entradaEstoque.get());
        }
        throw new EntradaEstoqueNotFoundException(id);
    }

    public List<MovimentacaoEstoqueResponseListDto> findByFilters(MovimentacaoEstoqueFilterDto filter) {
        List<EntradaEstoque> entidades = entradaEstoqueRepository.findAll(EntradaEstoqueSpecifications.comFiltros(filter));
        return entidades.stream().map(entradaEstoqueListMapper::toDto).toList();
    }
}
