package io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoEntradaEstoque.repository;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoEntradaEstoque.ProdutoEntradaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProdutoEntradaEstoqueRepository extends JpaRepository<ProdutoEntradaEstoque, Long>, JpaSpecificationExecutor<ProdutoEntradaEstoque> {

    @Query("""
            SELECT p
            FROM ProdutoEntradaEstoque p
            JOIN FETCH p.produto
            WHERE p.entradaEstoque.id = :entradaEstoqueId
            """)
    List<ProdutoEntradaEstoque> findAllByEntradaEstoqueId(Long entradaEstoqueId);

}