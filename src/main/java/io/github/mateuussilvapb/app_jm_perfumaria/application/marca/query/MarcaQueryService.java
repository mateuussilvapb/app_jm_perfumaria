package io.github.mateuussilvapb.app_jm_perfumaria.application.marca.query;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.marca.Marca;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.exceptions.MarcaNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.marca.repository.IMarcaRepository;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarcaQueryService {


    private final IMarcaRepository marcaRepository;

    public List<Marca> findAllAtivos() {
        return this.marcaRepository.findAllByStatus(Status.ATIVO);
    }

    public List<Marca> findAllInativos() {
        return this.marcaRepository.findAllByStatus(Status.INATIVO);
    }

    public List<Marca> findAllByTermAndStatus(String searchTerm, Status status) {
        List<Marca> marcas;

        if (status == Status.ATIVO) {
            marcas = this.findAllAtivos();
        } else {
            marcas = this.findAllInativos();
        }

        if (StringUtils.isNotBlank(searchTerm)) {
            marcas =
                    marcas.stream().filter(marca -> marca.matchSearchTerm(searchTerm)).collect(Collectors.toList());
        }

        return marcas;
    }

    public List<AutocompleteDTO> findAllToAutocompleteByTermAndStatus(String searchTerm, Status status) {
        return this.marcaRepository.findAllByStatusAndTermoAutocompleteDTO(searchTerm, status);
    }

    public Marca findById(Long id) {
        return this.marcaRepository.findById(id).orElseThrow(() -> new MarcaNotFoundException(id));
    }
}
