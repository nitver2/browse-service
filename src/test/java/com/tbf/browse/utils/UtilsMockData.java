package com.tbf.browse.utils;

import com.tbf.browse.entiry.Movie;

import java.util.ArrayList;
import java.util.List;

public class UtilsMockData {

    public static List<Movie> getMovies(){
        List<Movie> movies = new ArrayList<>();
        Movie movie1 = new Movie();
        movie1.setMovie_id(1);
        movie1.setTitle("Movie 1");
        movie1.setLanguage("English");
        movie1.setGenre("Action");
        movies.add(movie1);

        Movie movie2 = new Movie();
        movie2.setMovie_id(2);
        movie2.setTitle("Movie 2");
        movie2.setLanguage("English");
        movie2.setGenre("Drama");
        movies.add(movie2);

        return movies;
    }
}
