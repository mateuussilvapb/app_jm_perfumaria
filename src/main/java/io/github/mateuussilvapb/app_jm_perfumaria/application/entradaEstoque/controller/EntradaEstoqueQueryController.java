package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.controller;


import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueFilterDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.dto.EntradaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.dto.EntradaEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.dto.EntradaEstoqueToViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.query.EntradaEstoqueQueryService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/entradas-estoque/query")
public class EntradaEstoqueQueryController {

    private final EntradaEstoqueQueryService queryService;

    @GetMapping
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<EntradaEstoqueResponseDto>> findAll() {
        return new ResponseEntity<>(queryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/list")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<EntradaEstoqueResponseListDto>> findAllToList() {
        return new ResponseEntity<>(queryService.findAllToList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<EntradaEstoqueToViewUpdateResponseDto> findById(@PathVariable String id) {
        return new ResponseEntity<>(queryService.findById(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping("/searchByFilters")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<EntradaEstoqueResponseListDto>> getByFilters(@ModelAttribute MovimentacaoEstoqueFilterDto filtersDTO) {
        return new ResponseEntity<>(queryService.findByFilters(filtersDTO), HttpStatus.OK);
    }
}
