package io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.repository;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.ProdutoAutocompleteDTO;
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
    List<Produto> findAllProdutosByStatus(@Param("status") Status status);

    @Query("""
                        SELECT new io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.ProdutoAutocompleteDTO(p.id, p.nome)
                        FROM Produto p
                        WHERE p.status = :status
                          AND (
                               :nome IS NULL
                               OR :nome = ''
                               OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))
                               OR LOWER(p.descricao) LIKE LOWER(CONCAT('%', :nome, '%'))
                          )
            """)
    List<ProdutoAutocompleteDTO> findAllProdutosByStatusAutocompleteDTO(@Param("nome") String nome, @Param("status") Status status);

    @Query("SELECT p FROM Produto p WHERE p.situacao = :situacao AND p.status = :status")
    List<Produto> findAllProdutosBySituacaoAndStatus(@Param("situacao") Situacao situacao, @Param("status") Status status);
}
