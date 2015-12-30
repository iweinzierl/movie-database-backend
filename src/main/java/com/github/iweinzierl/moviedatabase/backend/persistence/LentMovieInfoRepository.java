package com.github.iweinzierl.moviedatabase.backend.persistence;

import com.github.iweinzierl.moviedatabase.backend.domain.LentMovieInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LentMovieInfoRepository extends MongoRepository<LentMovieInfo, String> {
}
