package io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.controller;


import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.dto.MovimentacaoEstoqueCreateUpdateDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.command.SaidaEstoqueCommandService;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.saidaEstoque.SaidaEstoque;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/saidas-estoque/command")
public class SaidaEstoqueCommandController {

    private final SaidaEstoqueCommandService commandService;


    @PostMapping
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<SaidaEstoque> create(@RequestBody @Valid MovimentacaoEstoqueCreateUpdateDTO dto) {
        SaidaEstoque created = commandService.createSaidaEstoqueComProdutos(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<SaidaEstoque> update(@PathVariable Long id,
                                                 @RequestBody @Valid MovimentacaoEstoqueCreateUpdateDTO dto) {
        SaidaEstoque updated = commandService.updateSaidaEstoque(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commandService.deleteSaidaEstoque(id);
        return ResponseEntity.noContent().build();
    }
}
