package com.alviter.movies.domain.converter;

import com.alviter.movies.domain.dto.MovieDTO;
import com.alviter.movies.repository.entity.MovieEntity;

import java.util.function.Function;

public class MovieToMovieDTOConverter implements Function<MovieEntity, MovieDTO> {
    @Override
    public MovieDTO apply(MovieEntity movieEntity) {
        return MovieDTO.builder()
                .id(movieEntity.getId())
                .url(movieEntity.getUrl())
                .name(movieEntity.getName())
                .category(movieEntity.getCategory())
                .classification(movieEntity.getClassification())
                .noFavorites(movieEntity.getNoFavorites())
                .ranking(movieEntity.getRanking())
                .releaseYear(movieEntity.getReleaseYear())
                .build();
    }
}