package io.github.mateuussilvapb.app_jm_perfumaria.infra.categoria.repository;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.categoria.Categoria;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT c FROM Categoria c WHERE c.status = :status")
    List<Categoria> findAllByStatus(@Param("status") Status status);

    @Query("""
                        SELECT new io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteDTO(c.id, c.nome)
                        FROM Categoria c
                        WHERE c.status = :status
                          AND (
                               :termo IS NULL
                               OR :termo = ''
                               OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
                               OR LOWER(c.descricao) LIKE LOWER(CONCAT('%', :termo, '%'))
                          )
            """)
    List<AutocompleteDTO> findAllByStatusAndTermoAutocompleteDTO(@Param("termo") String termo, @Param("status") Status status);

    @Query("SELECT c FROM Categoria c WHERE c.nome = :nome")
    Optional<Categoria> findByNome(@Param("nome") String nome);
}
