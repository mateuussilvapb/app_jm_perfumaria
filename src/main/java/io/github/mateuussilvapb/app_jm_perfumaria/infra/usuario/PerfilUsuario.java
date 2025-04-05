package io.github.mateuussilvapb.app_jm_perfumaria.infra.usuario;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum PerfilUsuario {

    ADMIN("admin", "Admin"),
    EMPLOYEE("employee", "Employee"),
    MANAGER("manager", "Manager");

    private final String authority;
    private final String descricao;

    public static Set<PerfilUsuario> perfiss(Collection<String> authorities) {
        return authorities.stream()
                .map(PerfilUsuario::findByAuthority)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }

    public static Set<PerfilUsuario> perfis(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .map(PerfilUsuario::findByAuthority)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }

    public static Optional<PerfilUsuario> findByAuthority(String authority) {
        for (final var perfil : PerfilUsuario.values()) {
            if (StringUtils.equals(perfil.authority, authority)) {
                return Optional.of(perfil);
            }
        }
        return Optional.empty();
    }

    public static Set<PerfilUsuario> all() {
        return Arrays.stream(values()).collect(Collectors.toSet());
    }
}