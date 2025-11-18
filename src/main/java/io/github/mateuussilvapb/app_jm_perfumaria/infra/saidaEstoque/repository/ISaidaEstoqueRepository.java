package io.github.mateuussilvapb.app_jm_perfumaria.infra.saidaEstoque.repository;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISaidaEstoqueRepository extends JpaRepository<SaidaEstoque, Long>, JpaSpecificationExecutor<SaidaEstoque> {

    @Query(value = """
            select distinct ee.*
            from tb_saida_estoque ee
            join tb_produto_saida_estoque pee on pee.saida_estoque_id = ee.id
            where pee.id_produto = :produtoId
            """, nativeQuery = true)
    List<SaidaEstoque> findAllByProdutoId(@Param("produtoId") Long produtoId);
}
