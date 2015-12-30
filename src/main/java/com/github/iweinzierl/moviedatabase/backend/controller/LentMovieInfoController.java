package com.github.iweinzierl.moviedatabase.backend.controller;

import com.github.iweinzierl.moviedatabase.backend.domain.LentMovieInfo;
import com.github.iweinzierl.moviedatabase.backend.domain.Movie;
import com.github.iweinzierl.moviedatabase.backend.persistence.LentMovieInfoRepository;
import com.github.iweinzierl.moviedatabase.backend.persistence.MovieRepository;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class LentMovieInfoController {

    private static final Logger LOG = LoggerFactory.getLogger(LentMovieInfoController.class);

    @Autowired
    private LentMovieInfoRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @RequestMapping(path = "/api/lent", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LentMovieInfo>> list() {
        final List<LentMovieInfo> allLentMovies = repository.findAll();

        if (allLentMovies == null || allLentMovies.isEmpty()) {
            LOG.info("No lent movies found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(allLentMovies);
    }

    @RequestMapping(path = "/api/lent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LentMovieInfo> lendMovie(@RequestBody LentMovieInfo lentMovieInfo) {
        final LentMovieInfo alreadyLentMovie = repository.findOne(lentMovieInfo.getMovieId());
        if (alreadyLentMovie != null) {
            LOG.warn("Movie '{}' is already lent to: {}", alreadyLentMovie.getMovieId(), alreadyLentMovie.getPerson());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        final Movie movie = movieRepository.findById(lentMovieInfo.getMovieId());
        if (movie == null) {
            LOG.warn("Movie '{}' not in collection!", lentMovieInfo.getMovieId());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (Strings.isNullOrEmpty(lentMovieInfo.getPerson())) {
            LOG.warn("{person} is null in lent movie info object!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (lentMovieInfo.getLentDate() == null) {
            LOG.debug("Set lent date to NOW");
            lentMovieInfo.setLentDate(LocalDate.now());
        }

        final LentMovieInfo saved = repository.save(lentMovieInfo);
        if (saved != null && !Strings.isNullOrEmpty(saved.getMovieId())) {
            return ResponseEntity.ok(saved);
        }

        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

    @RequestMapping(path = "/api/lent/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LentMovieInfo> getByMovieId(@PathVariable("id") String movieId) {
        final LentMovieInfo lentMovieInfo = repository.findOne(movieId);
        if (lentMovieInfo != null) {
            return ResponseEntity.ok(lentMovieInfo);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/api/lent/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LentMovieInfo> movieReturned(@PathVariable("id") String movieId) {
        final LentMovieInfo lentMovieInfo = repository.findOne(movieId);
        if (lentMovieInfo != null) {
            repository.delete(lentMovieInfo);
            return ResponseEntity.ok(lentMovieInfo);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
