package io.github.mateuussilvapb.app_jm_perfumaria.infra.categoria.repository;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.categoria.Categoria;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT p FROM Produto p WHERE p.status = :status")
    List<Categoria> findAllCategoriasByStatus(@Param("status") Status status);
}
