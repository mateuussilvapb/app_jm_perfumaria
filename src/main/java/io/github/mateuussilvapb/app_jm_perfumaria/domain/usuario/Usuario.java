package io.github.mateuussilvapb.app_jm_perfumaria.domain.usuario;

import io.github.mateuussilvapb.app_jm_perfumaria.util.ObjectUtil;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

import java.util.*;

public record Usuario(
        String login,
        String email,
        Set<PerfilUsuario> perfis,
        Map<String, ?> custom) {

    public Usuario(String login, String email, Set<PerfilUsuario> perfis, Map<String, ?> custom) {
        this.login = Validate.notBlank(login, "Login obrigatório");
        this.perfis = SetUtils.emptyIfNull(perfis);
        this.custom = Objects.requireNonNullElseGet(custom, Collections::emptyMap);
        this.email = email;
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> customValue(String key) {
        return Optional.ofNullable(custom.get(key)).map(value -> (T) value);
    }

    public Optional<String> customValueString(String key) {
        return customValue(key).flatMap(ObjectUtil::ifString);
    }

    public boolean containsPerfil(PerfilUsuario... required) {
        if (ArrayUtils.isEmpty(required)) {
            return false;
        }
        for (final var perfilUsuario : perfis) {
            for (final var perfilRequired : required) {
                if (perfilUsuario == perfilRequired) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAdmin() {
        return containsPerfil(PerfilUsuario.ADMIN);
    }

    @Override
    public String toString() {
        return login;
    }
}