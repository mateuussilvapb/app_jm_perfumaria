package io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoEntradaEstoque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoEntradaEstoqueRepository extends JpaRepository<ProdutoEntradaEstoque, Long> {
}
