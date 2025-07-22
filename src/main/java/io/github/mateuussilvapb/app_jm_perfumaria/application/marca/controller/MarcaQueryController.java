package io.github.mateuussilvapb.app_jm_perfumaria.application.marca.controller;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.query.MarcaQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.marca.Marca;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marcas/query")
public class MarcaQueryController {

    private final MarcaQueryService marcaQueryService;

    @GetMapping
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Marca>> getAll() {
        List<Marca> marcas = marcaQueryService.findAll();
        return new ResponseEntity<>(marcas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<Marca> getById(@PathVariable String id) {
        return new ResponseEntity<>(marcaQueryService.findById(Long.parseLong(id)), HttpStatus.OK);
    }

    @GetMapping("/ativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Marca>> getAllAtivos() {
        return new ResponseEntity<>(marcaQueryService.findAllAtivos(), HttpStatus.OK);
    }

    @GetMapping("/inativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Marca>> getAllInativos() {
        return new ResponseEntity<>(marcaQueryService.findAllInativos(), HttpStatus.OK);
    }

    @GetMapping("/searchByTerm/ativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Marca>> getAtivosByTerm(@RequestParam(name = "term", required = false) String searchTerm) {
        return new ResponseEntity<>(marcaQueryService.findAllByTermAndStatus(searchTerm, Status.ATIVO), HttpStatus.OK);
    }

    @GetMapping("/searchByTerm/inativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Marca>> getInativosByTerm(@RequestParam(name = "term", required = false) String searchTerm) {
        return new ResponseEntity<>(marcaQueryService.findAllByTermAndStatus(searchTerm, Status.INATIVO), HttpStatus.OK);
    }

    @GetMapping("/searchByTerm/autocomplete/ativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<AutocompleteDTO>> getAtivosByTermAutocomplete(@RequestParam(name = "term", required = false) String searchTerm) {
        return new ResponseEntity<>(marcaQueryService.findAllToAutocompleteByTermAndStatus(searchTerm, Status.ATIVO), HttpStatus.OK);
    }

    @GetMapping("/searchByTerm/autocomplete/inativos")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<AutocompleteDTO>> getInativosByTermAutocomplete(@RequestParam(name = "term", required = false) String searchTerm) {
        return new ResponseEntity<>(marcaQueryService.findAllToAutocompleteByTermAndStatus(searchTerm, Status.INATIVO), HttpStatus.OK);
    }

    @GetMapping("/searchByTermAndStatus")
    @RolesAllowed({"admin", "employee", "manager"})
    public ResponseEntity<List<Marca>> getByTermAndStatus(@RequestParam(name = "term", required = false) String searchTerm, @RequestParam(name = "status", required = false) Status status) {
        return new ResponseEntity<>(marcaQueryService.findAllByTermAndStatus(searchTerm, status), HttpStatus.OK);
    }
}