package io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.query;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.ProdutoSemMovimentacaoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.ProdutosBaixaQuantidadeDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.ValorTotalEstoqueDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.mapper.IProdutoToProdutoBaixaQuantidadeDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.repository.IProdutoRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service responsável por consultas do dashboard
 */
@Service
@RequiredArgsConstructor
public class DashboardQueryService {

	private final IProdutoRepository produtoRepository;
	private final IProdutoToProdutoBaixaQuantidadeDTO produtoToProdutoBaixaQuantidadeDTO;

	/**
	 * Calcula o valor total do estoque e informações relacionadas
	 * 
	 * @return DTO com valor total em custo, venda, margem de lucro e quantidades
	 */
	public ValorTotalEstoqueDTO calcularValorTotalEstoque() {
		// Busca os valores do banco de dados
		BigDecimal valorTotalCusto = produtoRepository.calcularValorTotalEstoqueCusto();
		BigDecimal valorTotalVenda = produtoRepository.calcularValorTotalEstoqueVenda();
		Long quantidadeTotalProdutos = produtoRepository.calcularQuantidadeTotalEstoque();
		Long quantidadeItensDiferentes = produtoRepository.contarProdutosAtivos();

		// Calcula a margem de lucro potencial
		BigDecimal margemLucroPotencial = BigDecimal.ZERO;
		if (valorTotalCusto.compareTo(BigDecimal.ZERO) > 0) {
			margemLucroPotencial = valorTotalVenda.subtract(valorTotalCusto)
					.divide(valorTotalCusto, 4, RoundingMode.HALF_UP)
					.multiply(BigDecimal.valueOf(100))
					.setScale(2, RoundingMode.HALF_UP);
		}

		// Calcula o lucro potencial
		BigDecimal lucroPotencial = valorTotalVenda.subtract(valorTotalCusto)
				.setScale(2, RoundingMode.HALF_UP);

		return new ValorTotalEstoqueDTO(
				valorTotalCusto,
				valorTotalVenda,
				lucroPotencial,
				margemLucroPotencial,
				quantidadeTotalProdutos,
				quantidadeItensDiferentes);
	}

	/**
	 * Busca todos os produtos com baixa quantidade em estoque (menos que 5 itens)
	 * 
	 * @return Lista de produtos com baixa quantidade em estoque
	 */
	public List<ProdutosBaixaQuantidadeDTO> findAllByQuantidadeEmEstoqueLessThanFive() {
		return produtoRepository.findAllByQuantidadeEmEstoqueLessThanFive()
				.stream()
				.map(produtoToProdutoBaixaQuantidadeDTO::toDto)
				.collect(Collectors.toList());
	}

	/**
	 * Busca todos os produtos sem ou com baixa movimentação de saída do estoque
	 * 
	 * @return Lista de produtos sem movimentação
	 */
	public List<ProdutoSemMovimentacaoDTO> listarProdutosSemMovimentacao(LocalDate dataLimite) {
		return produtoRepository.findProdutosSemMovimentacaoNative(dataLimite)
				.stream()
				.map(p -> new ProdutoSemMovimentacaoDTO(
						p.getId(),
						p.getNome(),
						p.getCategoria(),
						p.getMarca(),
						p.getQuantidadeEmEstoque(),
						p.getDataUltimaSaida(),
						p.getDiasSemMovimentacao()))
				.sorted((a, b) -> {
					Integer d1 = a.diasSemMovimentacao();
					Integer d2 = b.diasSemMovimentacao();

					// nulls primeiro
					if (d1 == null && d2 == null)
						return 0;
					if (d1 == null)
						return -1;
					if (d2 == null)
						return 1;

					// maiores valores primeiro
					return d2.compareTo(d1);
				})
				.toList();
	}

}