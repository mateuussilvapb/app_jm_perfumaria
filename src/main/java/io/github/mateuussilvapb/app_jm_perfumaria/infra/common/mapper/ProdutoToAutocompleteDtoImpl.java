package io.github.mateuussilvapb.app_jm_perfumaria.infra.common.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteIdStringDTO;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.mapper.IAutocompleteMapper;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.produto.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoToAutocompleteDtoImpl implements IAutocompleteMapper<Produto> {
    @Override
    public AutocompleteIdStringDTO toDto(Produto produto) {
        return new AutocompleteIdStringDTO(produto.getIdString(), produto.getNome());
    }
}
