package com.tbf.browse.service;

import com.tbf.browse.TestClass;
import com.tbf.browse.entiry.Movie;
import com.tbf.browse.utils.UtilsMockData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SearchServiceTest extends TestClass {

    @Mock
    private RedisService redisService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SearchService searchService;

    private List<Movie> mockMovies;

    @Value("${baseurl}")
    private String baseUrl;

    @BeforeEach
    public void setup() {
        mockMovies = UtilsMockData.getMovies();
    }

    @Test
    public void testFindMoviesFromRedis() {
        String city = "agra";
        when(redisService.generateKey(any(), any(), any(), any())).thenReturn(any());
        when(redisService.getFromRedis(city)).thenReturn(mockMovies);

        List<Movie> movies = searchService.findMovies(city, null, null, null);

        assertEquals(2, movies.size());
        assertEquals("Movie 1", movies.get(0).getTitle());
        assertEquals("Movie 2", movies.get(1).getTitle());
    }

    @Test
    public void testFindMoviesFromRestApi() {
        String city = "agra";
        when(redisService.generateKey(any(), any(), any(), any())).thenReturn(any());
        when(redisService.getFromRedis(city)).thenReturn(null); // Simulating no cache
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok(mockMovies)); // Mock the RestTemplate call

        List<Movie> movies = searchService.findMovies(city, null, null, null);

        assertEquals(2, movies.size());
        assertEquals("Movie 1", movies.get(0).getTitle());
        assertEquals("Movie 2", movies.get(1).getTitle());
    }

    @Test
    public void testBuildUrl() {
        String city = "agra";
        String movie = "Inception";
        String language = "English";
        String genre = "Sci-Fi";

        String url = searchService.buildUrl(city, movie, language, genre);

        String expectedUrl = "http://localhost:8081/api/v1/movie/agra?movie=Inception&language=English&genre=Sci-Fi";
        assertEquals(expectedUrl, url);
    }
}
