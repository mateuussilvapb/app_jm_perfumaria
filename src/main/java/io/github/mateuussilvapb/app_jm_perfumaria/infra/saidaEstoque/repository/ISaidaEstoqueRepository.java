package io.github.mateuussilvapb.app_jm_perfumaria.infra.saidaEstoque.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;

@Repository
public interface ISaidaEstoqueRepository
        extends JpaRepository<SaidaEstoque, Long>, JpaSpecificationExecutor<SaidaEstoque> {

    @Query(value = """
            select distinct ee.*
            from tb_saida_estoque ee
            join tb_produto_saida_estoque pee on pee.saida_estoque_id = ee.id
            where pee.id_produto = :produtoId
            """, nativeQuery = true)
    List<SaidaEstoque> findAllByProdutoId(@Param("produtoId") Long produtoId);

    @Query("""
                SELECT YEAR(se.dataSaidaEstoque) AS ano,
                       MONTH(se.dataSaidaEstoque) AS mes,
                       COUNT(DISTINCT se.id) AS quantidadeSaidas,
                       COALESCE(SUM(pse.quantidade), 0)
                FROM SaidaEstoque se
                LEFT JOIN se.saidasProdutos pse
                WHERE se.dataSaidaEstoque BETWEEN :inicio AND :fim
                AND se.situacao = 'CADASTRO_FINALIZADO'
                AND se.status = 'ATIVO'
                GROUP BY YEAR(se.dataSaidaEstoque), MONTH(se.dataSaidaEstoque)
                ORDER BY ano, mes
            """)
    List<Object[]> buscarResumoMensal(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim);

    @Query("""
                SELECT  YEAR(se.dataSaidaEstoque) AS ano,
                        MONTH(se.dataSaidaEstoque) AS mes,
                        COALESCE(SUM(pse.quantidade * pse.precoUnitario), 0) AS valorTotal,
                        COALESCE(SUM(pse.quantidade * pse.precoUnitario * pse.desconto), 0) AS descontoTotal,
                        COALESCE(SUM(pse.quantidade), 0) AS quantidadeTotal
                FROM SaidaEstoque se
                LEFT JOIN se.saidasProdutos pse
                WHERE se.dataSaidaEstoque BETWEEN :inicio AND :fim
                AND se.situacao = 'CADASTRO_FINALIZADO'
                AND se.status = 'ATIVO'
                GROUP BY YEAR(se.dataSaidaEstoque), MONTH(se.dataSaidaEstoque)
                ORDER BY ano, mes
            """)
    List<Object[]> buscarValorTotalMensal(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim);

}
