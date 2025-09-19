package io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.query;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions.ProdutoNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.dto.ProdutoSaidaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.mapper.IProdutoSaidaEstoqueToProdutoSaidaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoSaidaEstoque.ProdutoSaidaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoSaidaEstoque.repository.IProdutoSaidaEstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoSaidaEstoqueQueryService {

    private final IProdutoSaidaEstoqueRepository produtoSaidaEstoqueRepository;
    private final IProdutoSaidaEstoqueToProdutoSaidaEstoqueResponseListDto produtoSaidaEstoqueResponseListMapper;

    public ProdutoSaidaEstoque findById(Long id) {
        return this.produtoSaidaEstoqueRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
    }

    public List<ProdutoSaidaEstoque> findAll() {
        return this.produtoSaidaEstoqueRepository.findAll();
    }

    public List<ProdutoSaidaEstoqueResponseListDto> findAllBySaidaEstoqueId(Long saidaEstoqueId) {
        return this.produtoSaidaEstoqueRepository
                .findAllBySaidaEstoqueId(saidaEstoqueId)
                .stream()
                .map(produtoSaidaEstoqueResponseListMapper::toDto)
                .toList();
    }
}
