package io.github.mateuussilvapb.app_jm_perfumaria.infra.saidaEstoque;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaidaEstoqueRepository extends JpaRepository<SaidaEstoque, Long> {
}
