package com.github.iweinzierl.moviedatabase.backend.persistence;

import com.github.iweinzierl.moviedatabase.backend.domain.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {

    Movie findById(String id);

    Movie findByTitle(String title);
}
