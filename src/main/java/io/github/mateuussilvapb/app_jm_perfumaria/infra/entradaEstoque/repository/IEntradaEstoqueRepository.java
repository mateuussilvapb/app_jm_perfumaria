package io.github.mateuussilvapb.app_jm_perfumaria.infra.entradaEstoque.repository;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IEntradaEstoqueRepository extends JpaRepository<EntradaEstoque, Long>, JpaSpecificationExecutor<EntradaEstoque> {

    @Query(value = """
            select distinct ee.*
            from tb_entrada_estoque ee
            join tb_produto_entrada_estoque pee on pee.entrada_estoque_id = ee.id
            where pee.id_produto = :produtoId
            """, nativeQuery = true)
    List<EntradaEstoque> findAllByProdutoId(@Param("produtoId") Long produtoId);

    @Query("""
            SELECT YEAR(se.dataEntradaEstoque) AS ano,
                    MONTH(se.dataEntradaEstoque) AS mes,
                    COUNT(DISTINCT se.id) AS quantidadeEntradas,
                    COALESCE(SUM(pse.quantidade), 0)
            FROM EntradaEstoque se
            LEFT JOIN se.entradasProdutos pse
            WHERE se.dataEntradaEstoque BETWEEN :inicio AND :fim
            AND se.situacao = 'CADASTRO_FINALIZADO'
            AND se.status = 'ATIVO'
            GROUP BY YEAR(se.dataEntradaEstoque), MONTH(se.dataEntradaEstoque)
            ORDER BY ano, mes
        """)
    List<Object[]> buscarResumoMensal(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim);
}
