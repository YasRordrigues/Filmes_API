package org.example;

import lombok.Data;

@Data
public class Movie {
    private final String originalTitle;
    private final Float vote_average;
    private final String imageUrl;

    public Movie(String originalTitle, double voteAverage, String imageUrl) {
        this.originalTitle = originalTitle;
        this.vote_average = (float) voteAverage;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "originalTitle='" + originalTitle +
                "', vote_average=" + vote_average +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}