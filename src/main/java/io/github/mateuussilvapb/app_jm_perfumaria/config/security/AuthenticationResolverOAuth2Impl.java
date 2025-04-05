package io.github.mateuussilvapb.app_jm_perfumaria.config.security;

import io.github.mateuussilvapb.app_jm_perfumaria.infra.usuario.PerfilUsuario;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthenticationResolverOAuth2Impl implements AuthenticationResolver {

    private static final String CLAIM_EMAIL = "email";
    private static final String TOKEN = "token";

    private final OAuth2AuthenticationToken auth;

    @Override
    public Optional<Usuario> resolve() {
        final var defaultOidcUser = defaultOidcUser(auth.getPrincipal());
        final var email = defaultOidcUser.map(AuthenticationResolverOAuth2Impl::email).orElse(null);
        final var custom = defaultOidcUser.map(AuthenticationResolverOAuth2Impl::custom).orElseGet(Collections::emptyMap);
        return Optional.of(new Usuario(auth.getName(), email, PerfilUsuario.perfis(auth.getAuthorities()), custom));
    }

    private static Optional<DefaultOidcUser> defaultOidcUser(OAuth2User principal) {
        if (principal instanceof DefaultOidcUser defaultOidcUser) {
            return Optional.of(defaultOidcUser);
        }
        return Optional.empty();
    }

    private static String email(DefaultOidcUser user) {
        return user.getUserInfo().getClaim(CLAIM_EMAIL);
    }

    private static Map<String, ?> custom(DefaultOidcUser user) {
        return Optional.ofNullable(user.getIdToken()).map(token -> Map.of(TOKEN, token.getTokenValue())).orElseGet(Collections::emptyMap);
    }
}