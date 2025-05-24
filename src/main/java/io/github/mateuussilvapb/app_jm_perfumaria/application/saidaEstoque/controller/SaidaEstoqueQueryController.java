package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.controller;


import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.query.SaidaEstoqueQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;
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
@RequestMapping("/saidas-estoque/query")
public class SaidaEstoqueQueryController {

    private final SaidaEstoqueQueryService queryService;

    @GetMapping
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<SaidaEstoque>> findAll() {
        return new ResponseEntity<>(queryService.findAll(), HttpStatus.OK);
    }
}
