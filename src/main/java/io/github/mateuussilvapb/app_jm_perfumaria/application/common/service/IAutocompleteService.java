package io.github.mateuussilvapb.app_jm_perfumaria.application.common.service;

import io.github.mateuussilvapb.app_jm_perfumaria.shared.enums.Status;

import java.util.List;

public interface IAutocompleteService<A> {
    List<A> findAllToAutocompleteByTermAndStatus(String searchTerm, Status status);
}