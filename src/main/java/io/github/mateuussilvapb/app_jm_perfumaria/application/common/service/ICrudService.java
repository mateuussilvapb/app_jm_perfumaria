package io.github.mateuussilvapb.app_jm_perfumaria.application.common.service;

public interface ICrudService<T, A, C, U>
        extends IReadOnlyService<T>, IAutocompleteService<A>, IWriteService<T, C, U> {
}

