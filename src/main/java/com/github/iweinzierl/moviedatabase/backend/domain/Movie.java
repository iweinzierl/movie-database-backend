package com.github.iweinzierl.moviedatabase.backend.domain;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Set;

public class Movie {

    @Id
    private String id;

    private String title;
    private String originalTitle;

    private String description;
    private String coverUrl;

    private Set<String> genres;

    private int length;

    private LocalDate published;

    public Movie() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public LocalDate getPublished() {
        return published;
    }

    public void setPublished(LocalDate published) {
        this.published = published;
    }

    @Override
    public String toString() {
        return String.format("Movie[id=%s, title=%s, originalTitle=%s]", id, title, originalTitle);
    }
}
