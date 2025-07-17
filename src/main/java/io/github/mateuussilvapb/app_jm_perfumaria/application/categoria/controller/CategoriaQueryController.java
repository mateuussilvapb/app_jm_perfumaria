package io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.controller;

import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.query.CategoriaQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.categoria.Categoria;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorias/query")
public class CategoriaQueryController {

    private final CategoriaQueryService categoriaQueryService;

    @GetMapping
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Categoria>> getAll() {
        List<Categoria> categorias = categoriaQueryService.findAll();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<Categoria> getById(@PathVariable String id) {
        return new ResponseEntity<>(categoriaQueryService.findById(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping("/ativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Categoria>> getAllAtivos() {
        return new ResponseEntity<>(categoriaQueryService.findAllAtivos(), HttpStatus.OK);
    }

    @GetMapping("/inativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Categoria>> getAllInativos() {
        return new ResponseEntity<>(categoriaQueryService.findAllInativos(), HttpStatus.OK);
    }

    @GetMapping("/searchByTerm/ativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Categoria>> getAtivosByTerm(@RequestParam(name = "term", required = false) String searchTerm) {
        return new ResponseEntity<>(categoriaQueryService.findAllByTermAndStatus(searchTerm, Status.ATIVO), HttpStatus.OK);
    }

    @GetMapping("/searchByTerm/inativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Categoria>> getInativosByTerm(@RequestParam(name = "term", required = false) String searchTerm) {
        return new ResponseEntity<>(categoriaQueryService.findAllByTermAndStatus(searchTerm, Status.INATIVO), HttpStatus.OK);
    }

    @GetMapping("/searchByTerm/autocomplete/ativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<AutocompleteDTO>> getAtivosByTermAutocomplete(@RequestParam(name = "term", required = false) String searchTerm) {
        return new ResponseEntity<>(categoriaQueryService.findAllToAutocompleteByTermAndStatus(searchTerm, Status.ATIVO), HttpStatus.OK);
    }

    @GetMapping("/searchByTerm/autocomplete/inativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<AutocompleteDTO>> getInativosByTermAutocomplete(@RequestParam(name = "term", required = false) String searchTerm) {
        return new ResponseEntity<>(categoriaQueryService.findAllToAutocompleteByTermAndStatus(searchTerm, Status.INATIVO), HttpStatus.OK);
    }

    @GetMapping("/searchByTermAndStatus")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Categoria>> getByTermAndStatus(@RequestParam(name = "term", required = false) String searchTerm, @RequestParam(name = "status", required = false) Status status) {
        return new ResponseEntity<>(categoriaQueryService.findAllByTermAndStatus(searchTerm, status), HttpStatus.OK);
    }
}