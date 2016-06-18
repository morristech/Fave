package com.honeycrisp.fave.event;

import com.honeycrisp.fave.http.themoviedb.MovieSearchResults;

/**
 * HttpEvent for communicating the result of fetching movie search results.
 *
 * Created by Ryan Taylor on 6/6/16.
 */
public class MovieSearchEvent extends HttpEvent {

    public static class OnSuccess extends HttpEvent.OnSuccess<MovieSearchResults> {

        public OnSuccess(MovieSearchResults response) {
            super(response);
        }
    }

    public static class OnFailure extends HttpEvent.OnFailure {

        public OnFailure(Throwable throwable) {
            super(throwable);
        }
    }
}
