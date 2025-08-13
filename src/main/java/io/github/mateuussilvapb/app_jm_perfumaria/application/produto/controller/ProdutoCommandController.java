package io.github.mateuussilvapb.app_jm_perfumaria.application.produto.controller;

import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.command.ProdutoCommandService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.dto.CreateUpdateProdutoDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produtos/command")
public class ProdutoCommandController {

    private final ProdutoCommandService produtoCommandService;

    @PostMapping
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<Produto> create(@RequestBody @Valid CreateUpdateProdutoDTO produtoDTO) {
        Produto produto = produtoCommandService.create(produtoDTO);
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<Produto> update(@RequestBody @Valid CreateUpdateProdutoDTO produtoDTO, @PathVariable String id) {
        Produto produto = produtoCommandService.update(Long.parseLong(id), produtoDTO);
        return new ResponseEntity<>(produto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<Void> delete(@PathVariable String id) {
        produtoCommandService.deleteById(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/toogleStatus/{id}")
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<Produto> toogleStatus(@PathVariable String id) {
        Produto produto = produtoCommandService.toogleStatus(Long.parseLong(id));
        return new ResponseEntity<>(produto, HttpStatus.OK);
    }
}
