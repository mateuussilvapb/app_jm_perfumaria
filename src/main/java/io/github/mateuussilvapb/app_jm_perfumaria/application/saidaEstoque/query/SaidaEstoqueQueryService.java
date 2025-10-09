package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.query;

import static io.github.mateuussilvapb.app_jm_perfumaria.util.ComparatorsUtil.ordenarPorDoisCriterios;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueFilterDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueToViewUpdateResponseDto;
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

    public List<MovimentacaoEstoqueResponseDto> findAll() {
        return this.saidaEstoqueRepository.findAll().stream()
            .map(saidaEstoqueMapper::toDto)
            .sorted(ordenarPorDoisCriterios(
                MovimentacaoEstoqueResponseDto::situacao,
                MovimentacaoEstoqueResponseDto::codigo
            ))
            .toList();
    }

    public List<MovimentacaoEstoqueResponseListDto> findAllToList() {
        return this.saidaEstoqueRepository.findAll().stream()
            .map(saidaEstoqueListMapper::toDto)
            .sorted(ordenarPorDoisCriterios(
                MovimentacaoEstoqueResponseListDto::situacao,
                MovimentacaoEstoqueResponseListDto::codigo
            ))
            .toList();
    }

    public MovimentacaoEstoqueToViewUpdateResponseDto findById(Long id) {
        var saidaEstoque = this.saidaEstoqueRepository.findById(id);
        if (saidaEstoque.isPresent()) {
            return saidaEstoqueToViewUpdateMapper.toDto(saidaEstoque.get());
        }
        throw new SaidaEstoqueNotFoundException(id);
    }

    public List<MovimentacaoEstoqueResponseListDto> findByFilters(MovimentacaoEstoqueFilterDto filter) {
        List<SaidaEstoque> entidades = saidaEstoqueRepository.findAll(SaidaEstoqueSpecifications.comFiltros(filter));
        return entidades.stream().map(saidaEstoqueListMapper::toDto)
            .sorted(ordenarPorDoisCriterios(
                MovimentacaoEstoqueResponseListDto::situacao,
                MovimentacaoEstoqueResponseListDto::codigo
            ))
            .toList();
    }
}
