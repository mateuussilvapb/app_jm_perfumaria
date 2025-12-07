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
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.dto.ResumoMensalSaidaEstoqueDto;
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

	/**
	 * Endpoint para obter o resumo mensal de saida de estoque.
	 * Os parâmetros dataInicial e dataFinal são opcionais e definem o período para a consulta.
	 * Se não informados, o período padrão é de um ano até a data atual.
	 * 
	 * @param dataInicial Data inicial para a consulta
	 * @param dataFinal Data final para a consulta
	 * @return Lista de resumo mensal de saida de estoque
	 */
	@GetMapping("/resumo-mensal")
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<List<ResumoMensalSaidaEstoqueDto>> getResumoMensal(
            @RequestParam(required = false) String dataInicial,
            @RequestParam(required = false) String dataFinal) {
        
        // Define valores padrão: período de um ano até a data atual
        LocalDate fim = dataFinal != null && !dataFinal.trim().isEmpty() 
                ? LocalDate.parse(dataFinal.trim()) 
                : LocalDate.now();
        LocalDate inicio = dataInicial != null && !dataInicial.trim().isEmpty() 
                ? LocalDate.parse(dataInicial.trim()) 
                : fim.minusYears(1);
        
        return new ResponseEntity<>(dashboardQueryService.buscarResumoMensal(inicio, fim), HttpStatus.OK);
    }

}
