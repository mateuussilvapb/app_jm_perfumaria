package io.github.mateuussilvapb.app_jm_perfumaria.config.security;

import io.github.mateuussilvapb.app_jm_perfumaria.util.ObjectUtil;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@UtilityClass
class ClaimsRolesExtractor {

    private static final String ROLES = "roles";
    private static final String RESOURCE_ACCESS = "resource_access";
    private static final String CLIENT_ID = "app_jm_perfumaria";

    static Collection<? extends GrantedAuthority> extract(Jwt jwt) {
        return extract(jwt.getClaims()).stream().map(SimpleGrantedAuthority::new).toList();
    }

    static List<String> extract(Map<String, Object> claims) {
        final Map<String, Object> value = usuario(getResourceAccess(claims));
        return value == null ? Collections.emptyList() : ObjectUtil.cast(value.get(ROLES));
    }

    private static Map<String, Object> usuario(Map<String, Object> resourceAccess) {
        final Object value = resourceAccess.get(CLIENT_ID);
        return value == null ? Collections.emptyMap() : ObjectUtil.cast(value);
    }

    private static Map<String, Object> getResourceAccess(Map<String, Object> claims) {
        final Object value = claims.get(RESOURCE_ACCESS);
        return value == null ? Collections.emptyMap() : ObjectUtil.cast(value);
    }
}