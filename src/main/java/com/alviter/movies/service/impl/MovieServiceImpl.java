package com.alviter.movies.service.impl;

import com.alviter.movies.domain.converter.MovieToMovieDTOConverter;
import com.alviter.movies.domain.dto.MovieDTO;
import com.alviter.movies.exception.MovieException;
import com.alviter.movies.repository.MovieRepository;
import com.alviter.movies.repository.entity.MovieEntity;
import com.alviter.movies.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    private MovieToMovieDTOConverter movieToMovieDTOConverter = new MovieToMovieDTOConverter();

    @Override
    public List<MovieDTO> getAllByReleaseYear(String releaseYear) throws MovieException {

        log.info("Movie service getAllByReleaseYear: {}", releaseYear);

        List<MovieEntity> movieEntities = movieRepository.findMovieByReleaseYear(releaseYear).orElseThrow(() -> {
            log.error("Error getting movie list with RELEASE YEAR = {}", releaseYear);
            return new MovieException("Movie list not found records.");
        });

        if (movieEntities.isEmpty()) throw new MovieException("No records found");

        return movieEntities.stream().map(entity -> movieToMovieDTOConverter.apply(entity))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<MovieDTO>> getAllGroupByReleaseYear() throws MovieException {

        log.info("Movie service getAllGroupByReleaseYear.");
        Map<String, List<MovieDTO>> moviesGroup;

        try {
            moviesGroup = ((List<MovieEntity>) movieRepository.findAll()).stream()
                    .map(entity -> movieToMovieDTOConverter.apply(entity))
                    .collect(groupingBy(MovieDTO::getReleaseYear));
        } catch (Exception e) {
            log.info("Error getting movies grouped.", e);
            throw new MovieException("No data found.");
        }
        return moviesGroup;
    }

    @Override
    public void voteMovie(int id, boolean isUp) throws MovieException {
        log.info("Processing voteMovie service, id {}", id);

        MovieEntity movieEntity = movieRepository.findById(id).orElseThrow(() -> {
            log.error("Error getting movie fromDB id: {}", id);
            return new MovieException("Something went wrong searching for movie.");
        });

        movieEntity.setRanking(isUp ? movieEntity.getRanking() + 1 : movieEntity.getRanking() - 1);
        movieRepository.save(movieEntity);
    }

    @Override
    public MovieDTO getMovie(int id) throws MovieException {
        log.info("Movie service getMovie: {}", id);
        return movieToMovieDTOConverter.apply(movieRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Error getting movie with id = {}", id);
                    return new MovieException("Movie not found");
                }));
    }
}