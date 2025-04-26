package io.github.mateuussilvapb.app_jm_perfumaria.config.security;

import io.github.mateuussilvapb.app_jm_perfumaria.domain.usuario.PerfilUsuario;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.usuario.Usuario;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collections;
import java.util.Optional;

@UtilityClass
public class AuthenticationResolverFacade {

    private static final String ANONYMOUS = "anonymousUser";

    public static Optional<Usuario> resolve() {
        return resolve(SecurityContextHolder.getContext().getAuthentication());
    }

    public static boolean isAdmin() {
        return resolve().map(Usuario::isAdmin).orElse(false);
    }

    public static Optional<Usuario> resolve(Authentication auth) {
        return StringUtils.equals(auth.getName(), ANONYMOUS)
                ? Optional.empty()
                : jwt(auth).or(() -> oAuth2(auth)).or(() -> defaultAuth(auth));
    }

    private static Optional<Usuario> jwt(Authentication auth) {
        if (auth instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return new AuthenticationResolverJwtImpl(jwtAuthenticationToken).resolve();
        }
        return Optional.empty();
    }

    private static Optional<Usuario> oAuth2(Authentication auth) {
        if (auth instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken) {
            return new AuthenticationResolverOAuth2Impl(oAuth2AuthenticationToken).resolve();
        }
        return Optional.empty();
    }

    private static Optional<Usuario> defaultAuth(Authentication auth) {
        if (auth == null) {
            return Optional.empty();
        }
        if (auth.getPrincipal() instanceof UserDetails userDetails) {
            return Optional.of(new Usuario(
                    auth.getName(),
                    userDetails.getUsername(),
                    PerfilUsuario.perfis(auth.getAuthorities()),
                    Collections.emptyMap()));
        } else {
            String username = (String) auth.getPrincipal();
            return Optional.of(new Usuario(
                    auth.getName(),
                    username,
                    PerfilUsuario.perfis(auth.getAuthorities()),
                    Collections.emptyMap()));
        }
    }
}