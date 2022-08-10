package com.omdb.api.repositories;

import com.omdb.api.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface MoviesRepository extends JpaRepository<Movie,String> {
    Movie findByImdbID(String imdbID);
}
