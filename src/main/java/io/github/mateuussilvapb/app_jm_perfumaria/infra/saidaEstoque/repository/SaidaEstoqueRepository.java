package io.github.mateuussilvapb.app_jm_perfumaria.infra.saidaEstoque.repository;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaidaEstoqueRepository extends JpaRepository<SaidaEstoque, Long> {
}
