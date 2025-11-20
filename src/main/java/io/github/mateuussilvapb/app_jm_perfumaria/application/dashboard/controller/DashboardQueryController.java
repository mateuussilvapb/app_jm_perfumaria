package io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.controller;

import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.dto.ValorTotalEstoqueDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.dashboard.query.DashboardQueryService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
