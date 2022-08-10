package com.omdb.api.services;

import com.omdb.api.exception.util.UnAuthorizedBasicUser;
import com.omdb.api.model.Movie;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OmdbService {
    Movie getMovieDetail(Map<String, Object> params, User user) throws UnAuthorizedBasicUser;
    List<Movie> fetchAllMovies(User user, String sort, String by) throws UnAuthorizedBasicUser;
}
