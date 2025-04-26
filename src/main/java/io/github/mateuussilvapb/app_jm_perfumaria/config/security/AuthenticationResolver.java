package io.github.mateuussilvapb.app_jm_perfumaria.config.security;


import io.github.mateuussilvapb.app_jm_perfumaria.domain.usuario.Usuario;

import java.util.Optional;

@FunctionalInterface
public interface AuthenticationResolver {

    Optional<Usuario> resolve();
}