package com.spotifybt.Spotify.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * Configuración general para las credenciales y URLs necesarias para interactuar con la API de Spotify.
 */
@Component
public class SpotifySettings {

    private final String id;
    private final String secret;
    private final String callbackUrl;
    private final String authEndpoint;
    private final String tokenEndpoint;
    private final String permissionScope;
    private final String frontendUrl;


    public SpotifySettings(Dotenv dotenv) {
        this.id = dotenv.get("SPOTIFY_CLIENT_ID");
        this.secret = dotenv.get("SPOTIFY_CLIENT_SECRET");
        this.callbackUrl = dotenv.get("SPOTIFY_REDIRECT_URI");
        this.authEndpoint = dotenv.get("SPOTIFY_AUTHORIZE_URL");
        this.tokenEndpoint = dotenv.get("SPOTIFY_TOKEN_URL");
        this.permissionScope = dotenv.get("SPOTIFY_SCOPES");
        this.frontendUrl = dotenv.get("FRONTEND_URL");


        System.out.println("🎯 Loaded from .env:");
        System.out.println("clientId: " + id);
        System.out.println("callbackUrl: " + callbackUrl);
    }

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

    public String getFrontendUrl() {
    return frontendUrl;
}

    


}


