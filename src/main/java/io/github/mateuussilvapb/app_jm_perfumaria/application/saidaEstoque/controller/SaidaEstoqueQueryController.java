package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.controller;


import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueFilterDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.dto.SaidaEstoqueResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueResponseListDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.dto.SaidaEstoqueToViewUpdateResponseDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.query.SaidaEstoqueQueryService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/saidas-estoque/query")
public class SaidaEstoqueQueryController {

    private final SaidaEstoqueQueryService queryService;

    @GetMapping
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<SaidaEstoqueResponseDto>> findAll() {
        return new ResponseEntity<>(queryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/list")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<MovimentacaoEstoqueResponseListDto>> findAllToList() {
        return new ResponseEntity<>(queryService.findAllToList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<SaidaEstoqueToViewUpdateResponseDto> findById(@PathVariable String id) {
        return new ResponseEntity<>(queryService.findById(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping("/searchByFilters")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<MovimentacaoEstoqueResponseListDto>> getByFilters(@ModelAttribute MovimentacaoEstoqueFilterDto filtersDTO) {
        return new ResponseEntity<>(queryService.findByFilters(filtersDTO), HttpStatus.OK);
    }
}
