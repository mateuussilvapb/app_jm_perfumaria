package io.github.mateuussilvapb.app_jm_perfumaria.application.marca.controller;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.CreateUpdateDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.command.MarcaCommandService;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.marca.Marca;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/marcas/command")
public class MarcaCommandController {

    private final MarcaCommandService marcaCommandService;

    @PostMapping
    @RolesAllowed({"manager", "admin"})
    public ResponseEntity<Marca> save(@RequestBody CreateUpdateDto marca) {
        Marca saved = marcaCommandService.create(marca);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<Marca> update(@RequestBody CreateUpdateDto marcaDTO, @PathVariable String id) {
        Marca marca = marcaCommandService.update(Long.parseLong(id), marcaDTO);
        return new ResponseEntity<>(marca, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<Void> delete(@PathVariable String id) {
        marcaCommandService.deleteById(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/toogleStatus/{id}")
    @RolesAllowed({"admin", "manager"})
    public ResponseEntity<Marca> toogleStatus(@PathVariable String id) {
        Marca marca = marcaCommandService.toogleStatus(Long.parseLong(id));
        return new ResponseEntity<>(marca, HttpStatus.OK);
    }
}
