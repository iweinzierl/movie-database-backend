package com.github.iweinzierl.moviedatabase.backend.controller;

import com.github.iweinzierl.moviedatabase.backend.domain.LentMovieInfo;
import com.github.iweinzierl.moviedatabase.backend.persistence.LentMovieInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class PersonController {

    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private LentMovieInfoRepository lentMovieInfoRepository;

    @RequestMapping(path = "/api/person", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String>> list() {
        List<LentMovieInfo> lentMovieInfoList = lentMovieInfoRepository.findAll();

        if (lentMovieInfoList == null || lentMovieInfoList.isEmpty()) {
            LOG.debug("Found 0 people who lent movies");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Set<String> people = lentMovieInfoList
                .parallelStream()
                .map(LentMovieInfo::getPerson)
                .collect(Collectors.toSet());

        LOG.debug("Found {} people who lent movies", people.size());
        return ResponseEntity.ok(people);
    }
}
