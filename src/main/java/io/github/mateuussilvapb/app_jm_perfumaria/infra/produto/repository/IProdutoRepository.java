package io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.categoria.Categoria;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.marca.Marca;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.dashboard.projections.ProdutoSemMovimentacaoProjection;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

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
	List<AutocompleteDTO> findAllByStatusAndTermoAutocompleteDTO(@Param("termo") String termo,
			@Param("status") Status status);

	@Query("SELECT p FROM Produto p WHERE p.nome = :nome")
	Optional<Produto> findByNome(@Param("nome") String nome);

	@Query("SELECT p FROM Produto p WHERE p.categoria = :categoria")
	List<Produto> findByCategoria(@Param("categoria") Categoria categoria);

	@Query("SELECT p FROM Produto p WHERE p.marca = :marca")
	List<Produto> findByMarca(@Param("marca") Marca marca);

	/**
	 * Calcula o valor total do estoque baseado no preço de custo
	 * 
	 * @return Valor total (precoCusto * quantidadeEmEstoque) de todos os produtos
	 *         ativos
	 */
	@Query("""
			SELECT COALESCE(SUM(p.precoCusto * p.quantidadeEmEstoque), 0)
			FROM Produto p
			WHERE p.status = 'ATIVO'
			""")
	BigDecimal calcularValorTotalEstoqueCusto();

	/**
	 * Calcula o valor total do estoque baseado no preço de venda
	 * 
	 * @return Valor total (precoVenda * quantidadeEmEstoque) de todos os produtos
	 *         ativos
	 */
	@Query("""
			SELECT COALESCE(SUM(p.precoVenda * p.quantidadeEmEstoque), 0)
			FROM Produto p
			WHERE p.status = 'ATIVO'
			""")
	BigDecimal calcularValorTotalEstoqueVenda();

	/**
	 * Calcula a quantidade total de produtos em estoque
	 * 
	 * @return Soma de todas as quantidades em estoque dos produtos ativos
	 */
	@Query("""
			SELECT COALESCE(SUM(p.quantidadeEmEstoque), 0)
			FROM Produto p
			WHERE p.status = 'ATIVO'
			""")
	Long calcularQuantidadeTotalEstoque();

	/**
	 * Conta quantos produtos diferentes existem no estoque
	 * 
	 * @return Quantidade de produtos ativos
	 */
	@Query("SELECT COUNT(p) FROM Produto p WHERE p.status = 'ATIVO'")
	Long contarProdutosAtivos();

	/**
	 * Consulta todos os produtos com baixa quantidade em estoque (menos que 5
	 * itens)
	 * 
	 * @return Lista de produtos com baixa quantidade em estoque
	 */
	@Query("SELECT p FROM Produto p WHERE p.quantidadeEmEstoque < 5")
	List<Produto> findAllByQuantidadeEmEstoqueLessThanFive();

	/**
	 * Consulta todos os produtos sem ou com baixa movimentação de saída do estoque
	 * 
	 * @param dataLimite
	 * @return Lista de produto sem ou com baixa movimentação de saida do estoque
	 */
	@Query(value = """
			SELECT
			    p.id AS id,
			    p.nome AS nome,
			    c.nome AS categoria,
			    m.nome AS marca,
			    p.quantidade_estoque AS quantidadeEmEstoque,
			    MAX(sa.data_saida_estoque) AS dataUltimaSaida,
			    (CURRENT_DATE - MAX(sa.data_saida_estoque)) AS diasSemMovimentacao
			FROM tb_produto p
			LEFT JOIN tb_categoria c ON c.id = p.id_categoria
			LEFT JOIN tb_marca m ON m.id = p.id_marca
			LEFT JOIN tb_produto_saida_estoque pse ON pse.id_produto = p.id
			LEFT JOIN tb_saida_estoque sa ON sa.id = pse.saida_estoque_id
			GROUP BY
			    p.id,
			    p.nome,
			    c.nome,
			    m.nome,
			    p.quantidade_estoque
			HAVING
			    MAX(sa.data_saida_estoque) IS NULL
			    OR MAX(sa.data_saida_estoque) < :dataLimite
			""", nativeQuery = true)
	List<ProdutoSemMovimentacaoProjection> findProdutosSemMovimentacaoNative(LocalDate dataLimite);

}
