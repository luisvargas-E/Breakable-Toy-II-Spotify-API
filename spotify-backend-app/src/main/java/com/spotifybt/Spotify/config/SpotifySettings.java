/*Spotify Config*/
package com.spotifybt.Spotify.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración general para las credenciales y URLs necesarias para interactuar con la API de Spotify.
 */
@Configuration
public class SpotifySettings {

    @Value("${spotify.client-id}")
    private String id;

    @Value("${spotify.client-secret}")
    private String secret;

    @Value("${spotify.redirect-uri}")
    private String callbackUrl;

    @Value("${spotify.authorize-url}")
    private String authEndpoint;

    @Value("${spotify.token-url}")
    private String tokenEndpoint;

    @Value("${spotify.scopes}")
    private String permissionScope;

    // Métodos de acceso públicos (getters)

    public String fetchClientId() {
        return id;
    }

    public String fetchClientSecret() {
        return secret;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public String getAuthEndpoint() {
        return authEndpoint;
    }

    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    public String getPermissionScope() {
        return permissionScope;
    }
}
