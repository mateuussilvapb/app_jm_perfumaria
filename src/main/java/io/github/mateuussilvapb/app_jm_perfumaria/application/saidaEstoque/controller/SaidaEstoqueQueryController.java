package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueFilterDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueToViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.query.SaidaEstoqueQueryService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/saidas-estoque/query")
public class SaidaEstoqueQueryController {

    private final SaidaEstoqueQueryService queryService;

    @GetMapping
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<MovimentacaoEstoqueResponseDto>> findAll() {
        return new ResponseEntity<>(queryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/list")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<MovimentacaoEstoqueResponseListDto>> findAllToList() {
        return new ResponseEntity<>(queryService.findAllToList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<MovimentacaoEstoqueToViewUpdateResponseDto> findById(@PathVariable String id) {
        return new ResponseEntity<>(queryService.findById(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping("/searchByFilters")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<MovimentacaoEstoqueResponseListDto>> getByFilters(@ModelAttribute MovimentacaoEstoqueFilterDto filtersDTO) {
        return new ResponseEntity<>(queryService.findByFilters(filtersDTO), HttpStatus.OK);
    }
}
