package io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.query;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.categoria.Categoria;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.categoria.repository.CategoriaRepository;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaQueryService {

    CategoriaRepository categoriaRepository;

    @Transactional
    public List<Categoria> findAllAtivos() {
        return this.categoriaRepository.findAllCategoriasByStatus(Status.ATIVO);
    }

    @Transactional
    public List<Categoria> findAllInativos() {
        return this.categoriaRepository.findAllCategoriasByStatus(Status.INATIVO);
    }

    @Transactional
    public List<Categoria> findAllByTermAndStatus(String searchTerm, Status status) {
        List<Categoria> produtos;

        if (status == Status.ATIVO) {
            produtos = this.findAllAtivos();
        } else {
            produtos = this.findAllInativos();
        }

        if (StringUtils.isNotBlank(searchTerm)) {
            produtos = produtos.stream().filter(produto -> produto.matchSearchTerm(searchTerm)).collect(Collectors.toList());
        }

        return produtos;
    }

}
