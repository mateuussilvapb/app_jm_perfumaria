package io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.controller;


import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.dto.ProdutoEntradaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.query.ProdutoEntradaEstoqueQueryService;
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
@RequestMapping("/produtos-entradas-estoque/query")
public class ProdutoEntradaEstoqueQueryController {

    private final ProdutoEntradaEstoqueQueryService queryService;

    @GetMapping("/findByEntradaEstoqueId")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<ProdutoEntradaEstoqueResponseListDto>> findAllByEntradaEstoqueId(
            @RequestParam(name = "entradaEstoqueId") Long entradaEstoqueId
    ) {
        return new ResponseEntity<>(queryService.findAllByEntradaEstoqueId(entradaEstoqueId), HttpStatus.OK);
    }
}
