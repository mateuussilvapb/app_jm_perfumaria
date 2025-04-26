package io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.repository;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

    @Query("SELECT p FROM Produto p WHERE p.status = :status")
    List<Produto> findAllByStatus(@Param("status") Status status);

    @Query("""
                        SELECT new io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteDTO(p.id, p.nome)
                        FROM Produto p
                        WHERE p.status = :status
                          AND (
                               :termo IS NULL
                               OR :termo = ''
                               OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
                               OR LOWER(p.descricao) LIKE LOWER(CONCAT('%', :termo, '%'))
                          )
            """)
    List<AutocompleteDTO> findAllByStatusAndTermoAutocompleteDTO(@Param("termo") String termo, @Param(
            "status") Status status);

    @Query("SELECT p FROM Produto p WHERE p.situacao = :situacao AND p.status = :status")
    List<Produto> findAllBySituacaoAndStatus(@Param("situacao") Situacao situacao, @Param("status") Status status);
}
