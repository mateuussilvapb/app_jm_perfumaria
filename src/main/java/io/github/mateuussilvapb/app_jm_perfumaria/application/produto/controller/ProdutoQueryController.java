package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.controller;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.ProdutoFiltersDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.query.ProdutoQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Situacao;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/produtos/query")
public class ProdutoQueryController {

    private final ProdutoQueryService produtoQueryService;

    @GetMapping
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Produto>> getAll() {
        return new ResponseEntity<>(produtoQueryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<Produto> getById(
            @PathVariable String id
    ) {
        return new ResponseEntity<>(produtoQueryService.findById(Long.parseLong(id)),
                HttpStatus.OK);
    }

    @GetMapping("/ativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Produto>> getAllAtivos() {
        return new ResponseEntity<>(produtoQueryService.findAllAtivos(), HttpStatus.OK);
    }

    @GetMapping("/inativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Produto>> getAllInativos() {
        return new ResponseEntity<>(produtoQueryService.findAllInativos(), HttpStatus.OK);
    }

    @GetMapping("/searchByTerm/ativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Produto>> getAtivosByTerm(
            @RequestParam(name = "term", required = false) String searchTerm
    ) {
        return new ResponseEntity<>(produtoQueryService.findAllByTermAndStatus(searchTerm, Status.ATIVO),
                HttpStatus.OK);
    }

    @GetMapping("/searchByTerm/inativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Produto>> getInativosByTerm(
            @RequestParam(name = "term", required = false) String searchTerm
    ) {
        return new ResponseEntity<>(produtoQueryService.findAllByTermAndStatus(searchTerm,
                Status.INATIVO),
                HttpStatus.OK);
    }

    @GetMapping("/searchByTerm/autocomplete/ativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<AutocompleteDTO>> getAtivosByTermAutocomplete(
            @RequestParam(name = "term", required = false) String searchTerm
    ) {
        return new ResponseEntity<>(produtoQueryService.findAllToAutocompleteByTermAndStatus(searchTerm,
                Status.ATIVO),
                HttpStatus.OK);
    }

    @GetMapping("/searchByTerm/autocomplete/inativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<AutocompleteDTO>> getInativosByTermAutocomplete(
            @RequestParam(name = "term", required = false) String searchTerm
    ) {
        return new ResponseEntity<>(produtoQueryService.findAllToAutocompleteByTermAndStatus(searchTerm,
                Status.INATIVO),
                HttpStatus.OK);
    }

    @GetMapping("/searchBySituacao/ativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Produto>> getAtivosBySituacao(
            @RequestParam(name = "situacao") Situacao situacao
    ) {
        return new ResponseEntity<>(produtoQueryService.findAllBySituacaoAndStatus(situacao,
                Status.ATIVO),
                HttpStatus.OK);
    }

    @GetMapping("/searchBySituacao/inativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Produto>> getInativosBySituacao(
            @RequestParam(name = "situacao") Situacao situacao
    ) {
        return new ResponseEntity<>(produtoQueryService.findAllBySituacaoAndStatus(situacao,
                Status.INATIVO),
                HttpStatus.OK);
    }

    @GetMapping("/searchByFilters")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<Page<Produto>> getByFilters(
            @ModelAttribute ProdutoFiltersDTO filtersDTO,
            @PageableDefault(size = 20, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return new ResponseEntity<>(produtoQueryService.findByFilters(filtersDTO, pageable),
                HttpStatus.OK);
    }

}
