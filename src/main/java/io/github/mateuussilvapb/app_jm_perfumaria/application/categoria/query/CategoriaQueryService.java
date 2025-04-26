package io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.query;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.categoria.Categoria;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.categoria.exceptions.CategoriaNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.categoria.repository.ICategoriaRepository;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaQueryService {

    private final ICategoriaRepository categoriaRepository;

    public List<Categoria> findAllAtivos() {
        return this.categoriaRepository.findAllByStatus(Status.ATIVO);
    }

    public List<Categoria> findAllInativos() {
        return this.categoriaRepository.findAllByStatus(Status.INATIVO);
    }

    public List<Categoria> findAllByTermAndStatus(String searchTerm, Status status) {
        List<Categoria> categorias;

        if (status == Status.ATIVO) {
            categorias = this.findAllAtivos();
        } else {
            categorias = this.findAllInativos();
        }

        if (StringUtils.isNotBlank(searchTerm)) {
            categorias =
                    categorias.stream().filter(categoria -> categoria.matchSearchTerm(searchTerm)).collect(Collectors.toList());
        }

        return categorias;
    }

    public List<AutocompleteDTO> findAllToAutocompleteByTermAndStatus(String searchTerm, Status status) {
        return this.categoriaRepository.findAllByStatusAndTermoAutocompleteDTO(searchTerm, status);
    }

    public Categoria findById(Long id) {
        return this.categoriaRepository.findById(id).orElseThrow(() -> new CategoriaNotFoundException(id));
    }
}
