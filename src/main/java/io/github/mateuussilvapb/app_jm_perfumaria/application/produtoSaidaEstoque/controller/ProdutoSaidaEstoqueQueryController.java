package io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.controller;


import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.ProdutoMovimentacaoEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoSaidaEstoque.query.ProdutoSaidaEstoqueQueryService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produtos-saidas-estoque/query")
public class ProdutoSaidaEstoqueQueryController {

    private final ProdutoSaidaEstoqueQueryService queryService;

    @GetMapping("/findBySaidaEstoqueId")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<ProdutoMovimentacaoEstoqueResponseListDto>> findAllBySaidaEstoqueId(
            @RequestParam(name = "saidaEstoqueId") Long saidaEstoqueId
    ) {
        return new ResponseEntity<>(queryService.findAllBySaidaEstoqueId(saidaEstoqueId), HttpStatus.OK);
    }
}
