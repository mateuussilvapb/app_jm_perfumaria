package io.github.mateuussilvapb.app_jm_perfumaria.infra.entradaEstoque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntradaEstoqueRepository extends JpaRepository<EntradaEstoque, Long> {
}
