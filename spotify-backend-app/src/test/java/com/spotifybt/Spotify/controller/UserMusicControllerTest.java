package com.spotifybt.Spotify.controller;

import com.spotifybt.Spotify.dto.ArtistDto;
import com.spotifybt.Spotify.service.AccessTokenManager;
import com.spotifybt.Spotify.service.TokenCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.*;

import java.util.List;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserMusicControllerTest {

    @Mock
    private TokenCache tokenCache;

    @Mock
    private AccessTokenManager accessTokenManager;

    @InjectMocks
    private UserMusicController controller;

    @Spy
    @InjectMocks
    private UserMusicController spyController;

    private String validToken = "valid_token";

    private final String spotifyApiMockResponse = """
    {
      "items": [
        {
          "id": "1",
          "name": "Artist One",
          "images": [
            {
              "url": "http://image1.jpg"
            }
          ],
          "external_urls": {
            "spotify": "http://spotify.com/1"
          }
        },
        {
          "id": "2",
          "name": "Artist Two",
          "images": [],
          "external_urls": {
            "spotify": "http://spotify.com/2"
          }
        }
      ]
    }
    """;

    @BeforeEach
    void setUp() {
        spyController = Mockito.spy(new UserMusicController(tokenCache, accessTokenManager));
    }

    @Test
    void shouldReturnTopArtists_whenTokenIsValid() throws Exception {
        when(tokenCache.getAccessToken()).thenReturn(validToken);

        // Simulamos la respuesta de Spotify API
        ResponseEntity<String> mockResponse = new ResponseEntity<>(spotifyApiMockResponse, HttpStatus.OK);
        doReturn(mockResponse).when(spyController).requestTopArtistsFromSpotify(validToken);

        // Ejecutar
        ResponseEntity<List<ArtistDto>> response = spyController.fetchTopArtists();

        // Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        ArtistDto artist1 = response.getBody().get(0);
        assertEquals("1", artist1.getId());
        assertEquals("Artist One", artist1.getName());
        assertEquals("http://image1.jpg", artist1.getImage());
        assertEquals("http://spotify.com/1", artist1.getSpotifyUrl());

        ArtistDto artist2 = response.getBody().get(1);
        assertEquals("2", artist2.getId());
        assertEquals("Artist Two", artist2.getName());
        assertEquals("", artist2.getImage()); // No image
        assertEquals("http://spotify.com/2", artist2.getSpotifyUrl());
    }

    @Test
    void shouldReturnUnauthorized_whenTokenIsMissing() {
        when(tokenCache.getAccessToken()).thenReturn(null);

        ResponseEntity<List<ArtistDto>> response = controller.fetchTopArtists();

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }
}
