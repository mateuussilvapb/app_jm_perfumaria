package io.github.mateuussilvapb.app_jm_perfumaria.infra.common.service;

public interface ICrudService<T, A, C, U>
        extends IReadOnlyService<T>, IAutocompleteService<A>, IWriteService<T, C, U> {
}

