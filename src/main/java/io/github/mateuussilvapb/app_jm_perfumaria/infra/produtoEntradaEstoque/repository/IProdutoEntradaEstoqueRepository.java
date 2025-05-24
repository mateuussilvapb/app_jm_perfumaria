package io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoEntradaEstoque.repository;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IProdutoEntradaEstoqueRepository extends JpaRepository<ProdutoEntradaEstoque, Long>, JpaSpecificationExecutor<ProdutoEntradaEstoque> {



}
