package com.github.iweinzierl.moviedatabase.backend.domain;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class LentMovieInfo {

    @Id
    private String movieId;
    private String person;

    private LocalDate lentDate;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public LocalDate getLentDate() {
        return lentDate;
    }

    public void setLentDate(LocalDate lentDate) {
        this.lentDate = lentDate;
    }
}
