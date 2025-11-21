package io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.ProdutoSemMovimentacaoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.ProdutosBaixaQuantidadeDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.ValorTotalEstoqueDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.query.DashboardQueryService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;

/**
 * Controller responsável pelos endpoints de consulta do dashboard
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard/query")
public class DashboardQueryController {

	private final DashboardQueryService dashboardQueryService;

	/**
	 * Endpoint para obter o valor total do estoque
	 * 
	 * @return DTO com informações sobre valor total em custo, venda, margem e
	 *         quantidades
	 */
	@GetMapping("/valor-total-estoque")
	@RolesAllowed({ "admin", "manager" })
	public ResponseEntity<ValorTotalEstoqueDTO> getValorTotalEstoque() {
		ValorTotalEstoqueDTO valorTotal = dashboardQueryService.calcularValorTotalEstoque();
		return new ResponseEntity<>(valorTotal, HttpStatus.OK);
	}

	/**
	 * Endpoint para obter os produtos com baixa quantidade em estoque
	 * 
	 * @return Lista de produtos com baixa quantidade em estoque
	 */
	@RolesAllowed({ "admin", "manager" })
	@GetMapping("/produtos-baixa-quantidade")
	public List<ProdutosBaixaQuantidadeDTO> consultarProdutosBaixaQuantidade() {
		return dashboardQueryService.findAllByQuantidadeEmEstoqueLessThanFive();
	}

	/**
	 * Endpoint para obter os produtos sem ou com baixa movimentação de saida de estoque.
	 * O parâmetro dataLimite é opcional e define a data limite para a consulta.
	 * 
	 * @param dataLimite Data limite para a consulta
	 * @return Lista de produtos sem movimentação
	 */
	@RolesAllowed({ "admin", "manager" })
	@GetMapping("/produtos-sem-movimentacao")
	public List<ProdutoSemMovimentacaoDTO> consultarProdutosSemMovimentacao(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataLimite) {

		if (dataLimite == null) {
			dataLimite = LocalDate.now().minusDays(60);
		}

		return dashboardQueryService.listarProdutosSemMovimentacao(dataLimite);
	}

}
