package io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoSaidaEstoque.repository;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.produtoSaidaEstoque.ProdutoSaidaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoSaidaEstoqueRepository extends JpaRepository<ProdutoSaidaEstoque, Long> {
}
