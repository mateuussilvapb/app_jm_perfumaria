package io.github.mateuussilvapb.app_jm_perfumaria.infra.marca.repository;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.marca.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
