package io.github.mateuussilvapb.app_jm_perfumaria.infra.entradaEstoque.repository;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEntradaEstoqueRepository extends JpaRepository<EntradaEstoque, Long> {

    @Query(value = """
            select distinct ee.*
            from tb_entrada_estoque ee
            join tb_produto_entrada_estoque pee on pee.entrada_estoque_id = ee.id
            where pee.id_produto = :produtoId
            """, nativeQuery = true)
    List<EntradaEstoque> findAllByProdutoId(@Param("produtoId") Long produtoId);
}
