package com.github.iweinzierl.moviedatabase.backend.helper;

import com.github.iweinzierl.moviedatabase.backend.domain.Movie;
import com.github.iweinzierl.moviedatabase.backend.persistence.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public final class MovieHelper {

    @Autowired
    private MovieRepository repository;

    public Set<String> findDistinctGenres() {
        List<Movie> movies = repository.findAll();
        Set<String> genres = new HashSet<>();

        for (Movie movie : movies) {
            genres.addAll(movie.getGenres());
        }

        return genres;
    }
}
