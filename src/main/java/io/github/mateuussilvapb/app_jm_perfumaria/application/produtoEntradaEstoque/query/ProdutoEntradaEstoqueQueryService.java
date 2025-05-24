package io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.query;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions.ProdutoNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoEntradaEstoque.repository.IProdutoEntradaEstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoEntradaEstoqueQueryService {

    private final IProdutoEntradaEstoqueRepository produtoEntradaEstoqueRepository;

    public ProdutoEntradaEstoque findById(Long id) {
        return this.produtoEntradaEstoqueRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
    }

    public List<ProdutoEntradaEstoque> findAll() {
        return this.produtoEntradaEstoqueRepository.findAll();
    }
}
