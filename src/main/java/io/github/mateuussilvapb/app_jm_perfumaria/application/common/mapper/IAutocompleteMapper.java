package io.github.mateuussilvapb.app_jm_perfumaria.application.common.mapper;

import io.github.mateuussilvapb.app_jm_perfumaria.application.common.dto.AutocompleteIdStringDTO;

public interface IAutocompleteMapper<C> {
    AutocompleteIdStringDTO toDto(C c);
}
