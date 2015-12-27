package com.github.iweinzierl.moviedatabase.backend.domain;

public class Statistics {

    private int numberOfMovies;
    private int numberOfGenres;
    private int numberOfLentMovies;

    public Statistics(int numberOfMovies, int numberOfGenres, int numberOfLentMovies) {
        this.numberOfMovies = numberOfMovies;
        this.numberOfGenres = numberOfGenres;
        this.numberOfLentMovies = numberOfLentMovies;
    }

    public int getNumberOfMovies() {
        return numberOfMovies;
    }

    public void setNumberOfMovies(int numberOfMovies) {
        this.numberOfMovies = numberOfMovies;
    }

    public int getNumberOfGenres() {
        return numberOfGenres;
    }

    public void setNumberOfGenres(int numberOfGenres) {
        this.numberOfGenres = numberOfGenres;
    }

    public int getNumberOfLentMovies() {
        return numberOfLentMovies;
    }

    public void setNumberOfLentMovies(int numberOfLentMovies) {
        this.numberOfLentMovies = numberOfLentMovies;
    }
}
