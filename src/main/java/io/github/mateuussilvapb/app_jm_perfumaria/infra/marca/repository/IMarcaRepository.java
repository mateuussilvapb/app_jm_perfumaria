package io.github.mateuussilvapb.app_jm_perfumaria.infra.marca.repository;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.marca.Marca;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMarcaRepository extends JpaRepository<Marca, Long> {
    @Query("SELECT m FROM Marca m WHERE m.status = :status")
    List<Marca> findAllByStatus(@Param("status") Status status);

    @Query("""
                        SELECT new io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteDTO(m.id, m.nome)
                        FROM Marca m
                        WHERE m.status = :status
                          AND (
                               :termo IS NULL
                               OR :termo = ''
                               OR LOWER(m.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
                               OR LOWER(m.descricao) LIKE LOWER(CONCAT('%', :termo, '%'))
                          )
            """)
    List<AutocompleteDTO> findAllByStatusAndTermoAutocompleteDTO(@Param("termo") String termo, @Param("status") Status status);

    @Query("SELECT m FROM Marca m WHERE m.nome = :nome")
    Optional<Marca> findByNome(@Param("nome") String nome);
}
