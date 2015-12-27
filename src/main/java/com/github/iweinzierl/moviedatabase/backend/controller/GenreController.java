package com.github.iweinzierl.moviedatabase.backend.controller;

import com.github.iweinzierl.moviedatabase.backend.helper.MovieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class GenreController {

    @Autowired
    private MovieHelper movieHelper;

    @RequestMapping(path = "/api/genre", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String>> listGenres() {
        Set<String> genres = movieHelper.findDistinctGenres();

        return ResponseEntity.ok(genres);
    }
}
