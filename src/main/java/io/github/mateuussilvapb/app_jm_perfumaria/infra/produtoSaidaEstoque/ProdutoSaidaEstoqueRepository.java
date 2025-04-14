package io.github.mateuussilvapb.app_jm_perfumaria.infra.produtoSaidaEstoque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoSaidaEstoqueRepository extends JpaRepository<ProdutoSaidaEstoque, Long> {
}
