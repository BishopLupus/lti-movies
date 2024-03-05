package com.alviter.movies.repository;

import com.alviter.movies.repository.entity.MovieEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<MovieEntity, Integer> {

    Optional<List<MovieEntity>> findMovieByReleaseYear(String releaseYear);
}