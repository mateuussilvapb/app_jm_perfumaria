package io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.controller;

import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.command.CategoriaCommandService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.CreateUpdateDto;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.categoria.Categoria;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorias/command")
public class CategoriaCommandController {

    private final CategoriaCommandService categoriaCommandService;

    @PostMapping
    @RolesAllowed({"manager", "admin"})
    public ResponseEntity<Categoria> save(@RequestBody CreateUpdateDto categoria) {
        Categoria savedCategoria = categoriaCommandService.create(categoria);
        return new ResponseEntity<>(savedCategoria, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<Categoria> update(@RequestBody CreateUpdateDto categoriaDTO, @PathVariable String id) {
        Categoria categoria = categoriaCommandService.update(Long.parseLong(id), categoriaDTO);
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<Void> delete(@PathVariable String id) {
        categoriaCommandService.deleteById(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
