package com.honeycrisp.fave.http.themoviedb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ryan Taylor on 6/6/16.
 */
public interface TheMovieDbApi {

    String ENDPOINT = "https://api.themoviedb.org/3/";

    @GET("api/v1/stories")
    Call<MovieSearchResults> searchMovies(@Query("api_key") String apiKey,
                                          @Query("query") String query,
                                          @Query("page") Integer page);
}