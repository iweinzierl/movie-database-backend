package com.github.iweinzierl.moviedatabase.backend.controller;

import com.github.iweinzierl.moviedatabase.backend.domain.Movie;
import com.github.iweinzierl.moviedatabase.backend.domain.Statistics;
import com.github.iweinzierl.moviedatabase.backend.helper.MovieHelper;
import com.github.iweinzierl.moviedatabase.backend.persistence.LentMovieInfoRepository;
import com.github.iweinzierl.moviedatabase.backend.persistence.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class StatisticsController {

    @Autowired
    private MovieHelper movieHelper;

    @Autowired
    private LentMovieInfoRepository lentMovieInfoRepository;

    @Autowired
    private MovieRepository repository;

    @RequestMapping(path = "/api/statistics", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Statistics> getStatistics() {
        List<Movie> movies = repository.findAll();
        Set<String> genres = movieHelper.findDistinctGenres();
        long numberOfLentMovies = lentMovieInfoRepository.count();

        return ResponseEntity.ok(new Statistics(movies.size(), genres.size(), (int) numberOfLentMovies));
    }
}
