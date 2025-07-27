package com.spotifybt.Spotify.controller;

import com.spotifybt.Spotify.dto.SearchResultDto;
import com.spotifybt.Spotify.service.SpotifySearch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpotifySearchControllerTest {

    @Mock
    private SpotifySearch searchService;

    @InjectMocks
    private SpotifySearchController controller;

    @Test
    void shouldReturnSearchResults() {
        // Arrange
        String query = "Oasis";
        SearchResultDto mockResults = new SearchResultDto(); // Puedes poblarlo si quieres validar datos
        ResponseEntity<SearchResultDto> expectedResponse = ResponseEntity.ok(mockResults);

        when(searchService.search(query)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<SearchResultDto> response = controller.search(query);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertSame(mockResults, response.getBody());

        // Verifica que el servicio fue llamado correctamente
        verify(searchService, times(1)).search(query);
    }
}
