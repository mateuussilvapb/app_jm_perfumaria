package io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.query;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions.ProdutoNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.dto.ProdutoEntradaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.mapper.IProdutoEntradaEstoqueToProdutoEntradaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoEntradaEstoque.repository.IProdutoEntradaEstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoEntradaEstoqueQueryService {

    private final IProdutoEntradaEstoqueRepository produtoEntradaEstoqueRepository;
    private final IProdutoEntradaEstoqueToProdutoEntradaEstoqueResponseListDto produtoEntradaEstoqueResponseListMapper;

    public ProdutoEntradaEstoque findById(Long id) {
        return this.produtoEntradaEstoqueRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
    }

    public List<ProdutoEntradaEstoque> findAll() {
        return this.produtoEntradaEstoqueRepository.findAll();
    }

    public List<ProdutoEntradaEstoqueResponseListDto> findAllByEntradaEstoqueId(Long entradaEstoqueId) {
        return this.produtoEntradaEstoqueRepository
                .findAllByEntradaEstoqueId(entradaEstoqueId)
                .stream()
                .map(produtoEntradaEstoqueResponseListMapper::toDto)
                .toList();
    }
}
