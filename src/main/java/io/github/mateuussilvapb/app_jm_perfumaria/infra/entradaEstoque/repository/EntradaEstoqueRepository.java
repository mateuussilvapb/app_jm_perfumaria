package io.github.mateuussilvapb.app_jm_perfumaria.infra.entradaEstoque.repository;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntradaEstoqueRepository extends JpaRepository<EntradaEstoque, Long> {
}
