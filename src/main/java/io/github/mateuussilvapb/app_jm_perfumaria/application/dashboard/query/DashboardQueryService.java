package io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.query;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.MovimentacaoEstoqueQuantidadeItensDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.MovimentacaoEstoqueValorItensDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.ProdutoSemMovimentacaoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.ProdutosBaixaQuantidadeDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.ResumoMensalMovimentacaoEstoqueDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.ValorMensalMovimentacaoEstoqueDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.ValorTotalEstoqueDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.mapper.IProdutoToProdutoBaixaQuantidadeDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.entradaEstoque.repository.IEntradaEstoqueRepository;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.repository.IProdutoRepository;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.saidaEstoque.repository.ISaidaEstoqueRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service responsável por consultas do dashboard
 */
@Service
@RequiredArgsConstructor
public class DashboardQueryService {

	private final IProdutoRepository produtoRepository;
	private final IProdutoToProdutoBaixaQuantidadeDTO produtoToProdutoBaixaQuantidadeDTO;
	private final ISaidaEstoqueRepository saidaEstoqueRepository;
	private final IEntradaEstoqueRepository entradaEstoqueRepository;

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

	/**
	 * Converte as datas de entrada em objetos LocalDate, usando valores padrão se necessário
	 * 
	 * @param dataInicial Data inicial em formato String (pode ser nula ou vazia)
	 * @param dataFinal Data final em formato String (pode ser nula ou vazia)
	 * @return Array com [dataInicio, dataFim]
	 */
	private LocalDate[] parseDatasPeriodo(String dataInicial, String dataFinal) {
		LocalDate fim = dataFinal != null && !dataFinal.trim().isEmpty() 
				? LocalDate.parse(dataFinal.trim()) 
				: LocalDate.now();
		LocalDate inicio = dataInicial != null && !dataInicial.trim().isEmpty() 
				? LocalDate.parse(dataInicial.trim()) 
				: fim.minusYears(1).plusMonths(1);
		
		return new LocalDate[] { inicio, fim };
	}

	private Map<String, ResumoMensalMovimentacaoEstoqueDto> mapearResumoMensal(List<Object[]> resultado) {
		return resultado.stream()
			.map(row -> new ResumoMensalMovimentacaoEstoqueDto(
				((Number) row[0]).intValue(),  // ano
				((Number) row[1]).intValue(),  // mes
				((Number) row[2]).longValue(), // quantidadeSaidas
				((Number) row[3]).longValue()  // quantidadeTotal
			))
			.collect(Collectors.toMap(
				dto -> dto.ano() + "-" + dto.mes(),
				dto -> dto
			));
	}

	private Map<String, ValorMensalMovimentacaoEstoqueDto> mapearValorMensal(List<Object[]> resultado) {
		return resultado.stream()
			.map(row -> new ValorMensalMovimentacaoEstoqueDto(
				((Number) row[0]).intValue(),  // ano
				((Number) row[1]).intValue(),  // mes
				((Number) row[2]).longValue(), // valorTotal
				((Number) row[3]).longValue(), // descontoTotal
				((Number) row[4]).intValue()  // quantidadeTotal
			))
			.collect(Collectors.toMap(
				dto -> dto.ano() + "-" + dto.mes(),
				dto -> dto
			));
	}

	/**
	 * Busca o resumo mensal de saídas de estoque
	 * Retorna dados de todos os meses no período especificado, preenchendo com zero os meses sem movimentação
	 * 
	 * @param inicio Data de início do período
	 * @param fim Data de fim do período
	 * @return Lista com resumo de todos os meses no período entre inicio e fim
	 */
	public MovimentacaoEstoqueQuantidadeItensDTO buscarResumoItensMovimentacaoEstoqueMensal(String dataInicial, String dataFinal) {
		// Define valores padrão: período de um ano até a data atual
		LocalDate[] datas = parseDatasPeriodo(dataInicial, dataFinal);
		LocalDate inicio = datas[0];
		LocalDate fim = datas[1];

		List<Object[]> resultadoEntrada = entradaEstoqueRepository.buscarResumoMensal(inicio, fim);
		List<Object[]> resultadoSaida = saidaEstoqueRepository.buscarResumoMensal(inicio, fim);

		List<ResumoMensalMovimentacaoEstoqueDto> resumoEntrada = organizarResumoItensMovimentacaoEstoqueMensal(inicio, fim, resultadoEntrada);
		List<ResumoMensalMovimentacaoEstoqueDto> resumoSaida = organizarResumoItensMovimentacaoEstoqueMensal(inicio, fim, resultadoSaida);

		return new MovimentacaoEstoqueQuantidadeItensDTO(resumoEntrada, resumoSaida);
	}
	
	
	
	private List<ResumoMensalMovimentacaoEstoqueDto> organizarResumoItensMovimentacaoEstoqueMensal(LocalDate inicio, LocalDate fim, List<Object[]> resultado) {		
		var resumoPorMes = mapearResumoMensal(resultado);

		YearMonth inicioYearMonth = YearMonth.from(inicio);
		YearMonth fimYearMonth = YearMonth.from(fim);
		
		List<ResumoMensalMovimentacaoEstoqueDto> resultadoFinal = new ArrayList<>();
		YearMonth atual = inicioYearMonth;
        
		// Percorre todos os meses entre inicio e fim
        while (!atual.isAfter(fimYearMonth)) {
            int ano = atual.getYear();
            int mes = atual.getMonthValue();
            String chave = ano + "-" + mes;
            
            // Busca no mapa ou cria um DTO com zeros
            ResumoMensalMovimentacaoEstoqueDto dto = resumoPorMes.getOrDefault(
                chave,
                new ResumoMensalMovimentacaoEstoqueDto(ano, mes, 0L, 0L)
            );
            
            resultadoFinal.add(dto);
            atual = atual.plusMonths(1);
        }
        
        return resultadoFinal;
    }
	
	private List<ValorMensalMovimentacaoEstoqueDto> organizarValoresItensMovimentacaoEstoqueMensal(LocalDate inicio, LocalDate fim, List<Object[]> resultado) {		
		var resumoPorMes = mapearValorMensal(resultado);

		YearMonth inicioYearMonth = YearMonth.from(inicio);
		YearMonth fimYearMonth = YearMonth.from(fim);
		
		List<ValorMensalMovimentacaoEstoqueDto> resultadoFinal = new ArrayList<>();
		YearMonth atual = inicioYearMonth;
        
		// Percorre todos os meses entre inicio e fim
        while (!atual.isAfter(fimYearMonth)) {
            int ano = atual.getYear();
            int mes = atual.getMonthValue();
            String chave = ano + "-" + mes;
            
            // Busca no mapa ou cria um DTO com zeros
            ValorMensalMovimentacaoEstoqueDto dto = resumoPorMes.getOrDefault(
                chave,
                new ValorMensalMovimentacaoEstoqueDto(ano, mes, 0L, 0L, 0)
            );
            
            resultadoFinal.add(dto);
            atual = atual.plusMonths(1);
        }
        
        return resultadoFinal;
    }

	/**
	 * Busca o valor mensal de saídas de estoque
	 * Retorna dados de todos os meses no período especificado, preenchendo com zero os meses sem movimentação
	 * 
	 * @param inicio Data de início do período
	 * @param fim Data de fim do período
	 * @return Lista com valores de todos os meses no período entre inicio e fim
	 */
	public MovimentacaoEstoqueValorItensDTO buscarValorItensMovimentacaoEstoqueMensal(String dataInicial, String dataFinal) {
		// Define valores padrão: período de um ano até a data atual
		LocalDate[] datas = parseDatasPeriodo(dataInicial, dataFinal);
		LocalDate inicio = datas[0];
		LocalDate fim = datas[1];

		List<Object[]> resultadoEntrada = entradaEstoqueRepository.buscarValorTotalMensal(inicio, fim);
		List<Object[]> resultadoSaida = saidaEstoqueRepository.buscarValorTotalMensal(inicio, fim);

		List<ValorMensalMovimentacaoEstoqueDto> resumoEntrada = organizarValoresItensMovimentacaoEstoqueMensal(inicio, fim, resultadoEntrada);
		List<ValorMensalMovimentacaoEstoqueDto> resumoSaida = organizarValoresItensMovimentacaoEstoqueMensal(inicio, fim, resultadoSaida);

		return new MovimentacaoEstoqueValorItensDTO(resumoEntrada, resumoSaida);
	}

}