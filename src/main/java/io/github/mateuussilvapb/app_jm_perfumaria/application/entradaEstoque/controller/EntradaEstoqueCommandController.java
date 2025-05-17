package io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.controller;


import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.command.EntradaEstoqueCommandService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.dto.EntradaEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.entradaEstoque.EntradaEstoque;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/entradas-estoque/command")
public class EntradaEstoqueCommandController {

    private final EntradaEstoqueCommandService commandService;


    @PostMapping
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<EntradaEstoque> create(@RequestBody @Valid EntradaEstoqueCreateUpdateDTO dto) {
        EntradaEstoque created = commandService.createEntradaEstoqueComProdutos(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<EntradaEstoque> update(@PathVariable Long id, @RequestBody @Valid EntradaEstoqueCreateUpdateDTO dto) {
        EntradaEstoque updated = commandService.updateEntradaEstoque(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commandService.deleteEntradaEstoque(id);
        return ResponseEntity.noContent().build();
    }
}
