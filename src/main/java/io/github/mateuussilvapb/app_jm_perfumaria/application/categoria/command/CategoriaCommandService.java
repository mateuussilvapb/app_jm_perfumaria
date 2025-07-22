package io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.command;

import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.exceptions.CategoriaInUseException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.exceptions.CategoriaSameNameException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.query.CategoriaQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.CreateUpdateDto;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.query.ProdutoQueryService;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.categoria.Categoria;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.categoria.repository.ICategoriaRepository;
import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CategoriaCommandService {

    private final CategoriaQueryService categoriaQueryService;
    private final ProdutoQueryService produtoQueryService;
    private final ICategoriaRepository categoriaRepository;

    public void deleteById(Long id) {
        var categoria = validarCategoriaParaRemocaoOuDesabilitacao(id);
        this.categoriaRepository.deleteById(categoria.getId());
    }

    public Categoria update(Long id, CreateUpdateDto updatedCategoria) {
        var categoria = categoriaQueryService.findById(id);
        categoria.setNome(updatedCategoria.nome());
        categoria.setDescricao(updatedCategoria.descricao());
        categoria.setStatus(updatedCategoria.status());
        return categoriaRepository.save(categoria);
    }

    public Categoria create(CreateUpdateDto createdCategoria) {
        var categoriaSameName = categoriaRepository.findByNome(createdCategoria.nome());
        if (categoriaSameName.isPresent() && categoriaSameName.get().getStatus() == Status.INATIVO) {
            var updatedCategoria = new CreateUpdateDto(createdCategoria.nome(), createdCategoria.descricao(), Status.ATIVO);
            return this.update(categoriaSameName.get().getId(), updatedCategoria);
        }
        if (categoriaSameName.isPresent() && categoriaSameName.get().getStatus() == Status.ATIVO) {
            throw new CategoriaSameNameException(categoriaSameName.get().getNome());
        }
        var categoria = new Categoria(createdCategoria.nome(), createdCategoria.descricao(), Status.ATIVO, new ArrayList<>());
        return categoriaRepository.save(categoria);
    }

    public Categoria toogleStatus(Long id) {
        var categoria = categoriaQueryService.findById(id);
        Status statusAtual = categoria.getStatus();
        if (statusAtual.equals(Status.ATIVO)) {
            this.validarCategoriaParaRemocaoOuDesabilitacao(id);
            categoria.setStatus(Status.INATIVO);
        } else {
            categoria.setStatus(Status.ATIVO);
        }

        return categoriaRepository.save(categoria);
    }

    private Categoria validarCategoriaParaRemocaoOuDesabilitacao(Long id) {
        var categoria = categoriaQueryService.findById(id);
        var existsProdutos = produtoQueryService.findByCategoria(categoria);
        if (!existsProdutos.isEmpty()) {
            var nomesProdutos = existsProdutos.stream().map(Produto::getNome).toList();
            throw new CategoriaInUseException(categoria.getNome(), nomesProdutos);
        }
        return categoria;
    }
}
