package com.spotifybt.Spotify.service;

import com.spotifybt.Spotify.dto.SearchResultDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpotifySearchTest {

    @Mock
    private TokenCache tokenCache;

    @Mock
    private AccessTokenManager accessTokenManager;

    @InjectMocks
    @Spy
    private SpotifySearch spotifySearch;

    private final String validToken = "valid_token";

    private final String jsonMockResponse = """
        {
          "artists": {
            "items": [
              {
                "id": "1",
                "name": "Artist One",
                "images": [{ "url": "http://image.com/artist.jpg" }],
                "external_urls": { "spotify": "http://spotify.com/artist1" }
              }
            ]
          },
          "tracks": {
            "items": [
              {
                "id": "2",
                "name": "Track One",
                "artists": [{ "id": "1", "name": "Artist One" }],
                "album": {
                  "images": [{ "url": "http://image.com/track.jpg" }]
                },
                "external_urls": { "spotify": "http://spotify.com/track1" }
              }
            ]
          },
          "albums": {
            "items": [
              {
                "id": "3",
                "name": "Album One",
                "artists": [{ "name": "Artist One" }],
                "images": [{ "url": "http://image.com/album.jpg" }],
                "external_urls": { "spotify": "http://spotify.com/album1" }
              }
            ]
          }
        }
        """;

    @BeforeEach
    void setUp() {
        spotifySearch = Mockito.spy(new SpotifySearch(tokenCache, accessTokenManager));
    }

    @Test
    void shouldReturnSearchResults_whenTokenValid() {
        // Arrange
        when(tokenCache.getAccessToken()).thenReturn(validToken);

        ResponseEntity<String> mockResponse = ResponseEntity.ok(jsonMockResponse);
        doReturn(mockResponse).when(spotifySearch)
                .sendSearchRequest(eq("Oasis"), eq("artist,track,album"), eq(validToken));

        // Act
        ResponseEntity<SearchResultDto> response = spotifySearch.search("Oasis");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getArtists().size());
        assertEquals(1, response.getBody().getTracks().size());
        assertEquals(1, response.getBody().getAlbums().size());
    }

    @Test
    void shouldReturnUnauthorized_whenTokenIsNull() {
        when(tokenCache.getAccessToken()).thenReturn(null);

        ResponseEntity<SearchResultDto> response = spotifySearch.search("Oasis");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldAttemptRefresh_whenTokenUnauthorized() {
        // Arrange
        when(tokenCache.getAccessToken()).thenReturn(validToken);
        when(accessTokenManager.updateAccessToken()).thenReturn(true);
        when(tokenCache.getAccessToken()).thenReturn(validToken); // después del refresh

        ResponseEntity<String> unauthorizedResponse = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        ResponseEntity<String> refreshedResponse = ResponseEntity.ok(jsonMockResponse);

        doReturn(unauthorizedResponse)
                .doReturn(refreshedResponse)
                .when(spotifySearch).sendSearchRequest(anyString(), anyString(), anyString());

        // Act
        ResponseEntity<SearchResultDto> response = spotifySearch.search("Oasis");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(accessTokenManager).updateAccessToken();
    }
}
