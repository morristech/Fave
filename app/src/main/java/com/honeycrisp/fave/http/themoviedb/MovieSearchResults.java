package com.honeycrisp.fave.http.themoviedb;

import com.google.gson.annotations.SerializedName;
import com.honeycrisp.fave.model.Movie;

import java.util.List;

/**
 * Created by Ryan Taylor on 6/6/16.
 */
public class MovieSearchResults {

    @SerializedName("page") private int page;
    @SerializedName("results") private List<Movie> movies;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
