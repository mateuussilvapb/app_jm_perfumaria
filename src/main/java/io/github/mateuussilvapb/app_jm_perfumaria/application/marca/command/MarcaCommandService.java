package io.github.mateuussilvapb.app_jm_perfumaria.application.marca.command;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.CreateUpdateDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.query.MarcaQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.marca.Marca;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.marca.exceptions.MarcaSameNameException;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.marca.repository.IMarcaRepository;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MarcaCommandService {

    private final MarcaQueryService marcaQueryService;
    private final IMarcaRepository marcaRepository;

    public void deleteById(Long id) {
        var marca = marcaQueryService.findById(id);
        marca.setStatus(Status.INATIVO);
        this.update(id, new CreateUpdateDto(marca.getNome(), marca.getDescricao(),
                marca.getStatus()));
    }

    public Marca update(Long id, CreateUpdateDto updated) {
        var marca = marcaQueryService.findById(id);
        marca.setNome(updated.nome());
        marca.setDescricao(updated.descricao());
        marca.setStatus(updated.status());
        return marcaRepository.save(marca);
    }

    public Marca create(CreateUpdateDto created) {
        var marcaSameName = marcaRepository.findByNome(created.nome());
        if (marcaSameName.isPresent() && marcaSameName.get().getStatus() == Status.INATIVO) {
            var updatedMarca = new CreateUpdateDto(created.nome(),
                    created.descricao(), Status.ATIVO);
            return this.update(marcaSameName.get().getId(), updatedMarca);
        }
        if (marcaSameName.isPresent() && marcaSameName.get().getStatus() == Status.ATIVO) {
            throw new MarcaSameNameException(marcaSameName.get().getNome());
        }
        var marca = new Marca(created.nome(), created.descricao(),
                Status.ATIVO, new ArrayList<>());
        return marcaRepository.save(marca);
    }
}
