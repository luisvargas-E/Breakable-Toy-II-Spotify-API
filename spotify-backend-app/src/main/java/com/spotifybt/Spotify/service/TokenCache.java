/*TokenStore*/
package com.spotifybt.Spotify.service;


import org.springframework.stereotype.Component;

/**
 * Componente que actúa como almacenamiento en memoria para los tokens de autenticación de Spotify.
 * Nota: Los tokens se pierden al reiniciar la aplicación.
 */
@Component
public class TokenCache {

    private String currentAccessToken;
    private String currentRefreshToken;

    /**
     * Obtiene el access token actual.
     */
    public String getAccessToken() {
        return currentAccessToken;
    }

    /**
     * Guarda un nuevo access token.
     */
    public void saveAccessToken(String token) {
        this.currentAccessToken = token;
    }

    /**
     * Obtiene el refresh token actual.
     */
    public String getRefreshToken() {
        return currentRefreshToken;
    }

    /**
     * Guarda un nuevo refresh token.
     */
    public void saveRefreshToken(String token) {
        this.currentRefreshToken = token;
    }
}
