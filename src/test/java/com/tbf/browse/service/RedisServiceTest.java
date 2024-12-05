package com.tbf.browse.service;

import com.tbf.browse.TestClass;
import com.tbf.browse.entiry.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class RedisServiceTest extends TestClass {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private RedisService redisService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        when(redisTemplate.opsForValue()).thenReturn(valueOperations); // Mock opsForValue
    }

    @Test
    void testSaveToRedis() {
        // Arrange
        String key = "city";
        List<Movie> movies = Arrays.asList(new Movie(), new Movie()); // Sample movie list

        // Act
        redisService.saveToRedis(key, movies);

        // Assert
        verify(valueOperations).set(key, movies, 1, TimeUnit.DAYS); // Verify that set() was called with the list of movies
    }

    @Test
    void testGetFromRedis() {
        // Arrange
        String key = "city";
        List<Movie> expectedMovies = Arrays.asList(new Movie(), new Movie()); // Sample movie list
        when(valueOperations.get(key)).thenReturn(expectedMovies); // Mock get() to return a list of movies

        // Act
        List<Movie> actualMovies = redisService.getFromRedis(key);

        // Assert
        assertEquals(expectedMovies, actualMovies); // Verify that the returned list is correct
    }
}
