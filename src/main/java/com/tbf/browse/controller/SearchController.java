package com.tbf.browse.controller;

import com.tbf.browse.entiry.Movie;
import com.tbf.browse.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/{city}")
    public List<Movie> findMovies(@PathVariable String city,
                                 @RequestParam(required = false) String movie,
                                 @RequestParam(required = false) String language,
                                 @RequestParam(required = false) String genre){
        return searchService.findMovies(city, movie, language, genre);
    }
}
