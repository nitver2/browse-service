package com.tbf.browse.service;

import com.tbf.browse.entiry.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${baseurl}")
    private String baseUrl = "http://localhost:8081/api/v1/movie/";

//    @HystrixCommand(
//            fallbackMethod = "getFallbackMovies",
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
//            })
    public List<Movie> findMovies(String city, String movie, String language, String genre) {
        List<Movie> movies = redisService.getFromRedis(redisService.generateKey(city, movie, language, genre));
        if(movies == null){
            movies = getMovieList(city, movie, language, genre);
            saveToCache(city, movie, language, genre, movies);
        }
        return movies;
    }

    private void saveToCache(String city, String movie, String language, String genre, List<Movie> movies) {
        if(!movies.isEmpty()) redisService.saveToRedis(redisService.generateKey(city, movie, language, genre), movies);
    }

    private List<Movie> getMovieList(String city, String movie, String language, String genre) {
        ParameterizedTypeReference<List<Movie>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<Movie>> response = restTemplate.exchange(
                buildUrl(city, movie, language, genre),
                HttpMethod.GET,
                null,
                responseType
        );
        return response.getBody();
    }

    String buildUrl(String city, String movie, String language, String genre) {
        StringBuilder url = new StringBuilder(baseUrl).append(city);
        List<String> params = new ArrayList<>();
        if (movie != null) params.add("movie=" + movie);
        if (language != null) params.add("language=" + language);
        if (genre != null) params.add("genre=" + genre);
        if (!params.isEmpty()) {
            url.append("?").append(String.join("&", params));
        }
        return url.toString();
    }


    public List<Movie> getFallbackMovies() {
        return List.of(new Movie(), new Movie());
    }
}
