package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.controller;


import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.query.EntradaEstoqueQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/entradas-estoque/query")
public class EntradaEstoqueQueryController {

    private final EntradaEstoqueQueryService queryService;

    @GetMapping
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<EntradaEstoque>> findAll() {
        return new ResponseEntity<>(queryService.findAll(), HttpStatus.OK);
    }
}
