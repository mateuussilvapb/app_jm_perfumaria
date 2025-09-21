package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.query;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueFilterDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.dto.SaidaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.dto.SaidaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.dto.SaidaEstoqueToViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.exceptions.SaidaEstoqueNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.mapper.ISaidaEstoqueToSaidaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.mapper.ISaidaEstoqueToSaidaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.mapper.ISaidaEstoqueToSaidaEstoqueViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.query.specification.SaidaEstoqueSpecifications;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.saidaEstoque.repository.ISaidaEstoqueRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SaidaEstoqueQueryService {

    private final ISaidaEstoqueRepository saidaEstoqueRepository;
    private final ISaidaEstoqueToSaidaEstoqueResponseDto saidaEstoqueMapper;
    private final ISaidaEstoqueToSaidaEstoqueResponseListDto saidaEstoqueListMapper;
    private final ISaidaEstoqueToSaidaEstoqueViewUpdateResponseDto saidaEstoqueToViewUpdateMapper;

    public List<SaidaEstoqueResponseDto> findAll() {
        return this.saidaEstoqueRepository.findAll().stream().map(saidaEstoqueMapper::toDto).toList();
    }

    public List<SaidaEstoqueResponseListDto> findAllToList() {
        return this.saidaEstoqueRepository.findAll().stream().map(saidaEstoqueListMapper::toDto).toList();
    }

    public SaidaEstoqueToViewUpdateResponseDto findById(Long id) {
        var saidaEstoque = this.saidaEstoqueRepository.findById(id);
        if (saidaEstoque.isPresent()) {
            return saidaEstoqueToViewUpdateMapper.toDto(saidaEstoque.get());
        }
        throw new SaidaEstoqueNotFoundException(id);
    }

    public List<SaidaEstoqueResponseListDto> findByFilters(MovimentacaoEstoqueFilterDto filter) {
        List<SaidaEstoque> entidades = saidaEstoqueRepository.findAll(SaidaEstoqueSpecifications.comFiltros(filter));
        return entidades.stream().map(saidaEstoqueListMapper::toDto).toList();
    }
}
