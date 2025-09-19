package io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoSaidaEstoque.repository;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoSaidaEstoque.ProdutoSaidaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProdutoSaidaEstoqueRepository extends JpaRepository<ProdutoSaidaEstoque, Long>, JpaSpecificationExecutor<ProdutoSaidaEstoque> {

    @Query("""
            SELECT p
            FROM ProdutoSaidaEstoque p
            JOIN FETCH p.produto
            WHERE p.saidaEstoque.id = :saidaEstoqueId
            """)
    List<ProdutoSaidaEstoque> findAllBySaidaEstoqueId(Long saidaEstoqueId);

}