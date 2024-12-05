package com.tbf.browse.service;

import com.tbf.browse.entiry.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveToRedis(String key, Object data) {
        redisTemplate.opsForValue().set(key, data, 1, TimeUnit.DAYS); // Expire after 1 hour
    }

    public List<Movie> getFromRedis(String key) {
        return (List<Movie>) redisTemplate.opsForValue().get(key); // Get the value from Redis
    }

    String generateKey(String city, String movie, String language, String genre) {
        StringBuilder keyBuilder = new StringBuilder(city); // Always include city

        // Conditionally append optional parameters to the key
        if (movie != null && !movie.isEmpty()) {
            keyBuilder.append("_").append(movie);
        }
        if (language != null && !language.isEmpty()) {
            keyBuilder.append("_").append(language);
        }
        if (genre != null && !genre.isEmpty()) {
            keyBuilder.append("_").append(genre);
        }

        return keyBuilder.toString();
    }
}
