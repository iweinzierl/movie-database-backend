package com.github.iweinzierl.moviedatabase.backend.controller;

import com.github.iweinzierl.moviedatabase.backend.domain.Movie;
import com.github.iweinzierl.moviedatabase.backend.persistence.MovieRepository;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    private static final Logger LOG = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieRepository repository;

    @RequestMapping(path = "/api/movie", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> listMovies() {
        final List<Movie> movies = repository.findAll();

        if (movies != null && !movies.isEmpty()) {
            LOG.info("Found {} movies", movies.size());
            return ResponseEntity.ok(movies);
        } else {
            LOG.warn("No movies found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/api/movie", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        final Movie savedMovie = repository.save(movie);

        if (savedMovie != null && !Strings.isNullOrEmpty(savedMovie.getId())) {
            LOG.info("Persisted movie: {}", savedMovie);
            return ResponseEntity.ok(movie);
        } else {
            LOG.warn("Persisting movie failed: {}", movie);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(path = "/api/movie/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> getMovie(@PathVariable String id) {
        final Movie movie = repository.findById(id);

        if (movie != null) {
            LOG.info("Found movie by id '{}': {}", id, movie);
            return ResponseEntity.ok(movie);
        } else {
            LOG.warn("Did not find movie by id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/api/movie/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> deleteMovie(@PathVariable String id) {
        final Movie movie = repository.findById(id);

        if (movie != null) {
            repository.delete(movie);
            LOG.info("Deleted movie: {}", movie);

            return ResponseEntity.ok(movie);
        } else {
            LOG.warn("Failed to delete movie - reason = unknown id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
