package com.alviter.movies.service;

import com.alviter.movies.domain.dto.MovieDTO;
import com.alviter.movies.exception.MovieException;
import com.alviter.movies.repository.MovieRepository;
import com.alviter.movies.repository.entity.MovieEntity;
import com.alviter.movies.service.impl.MovieServiceImpl;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MovieServiceTest {

    @Tested
    private MovieService movieService;
    @Injectable
    private MovieRepository movieRepository;
    private List<MovieEntity> movieEntityList;
    private MovieEntity movieEntity;

    @Before
    public void init() {
        movieService = new MovieServiceImpl();
        movieEntityList = List.of(MovieEntity.builder()
                        .id(2)
                        .releaseYear("2022")
                        .url("https://www.google.com/test_ima.png")
                        .name("test movie 22")
                        .ranking(55)
                        .noFavorites(45)
                        .classification("+18")
                        .category("Comedy")
                        .build(),
                MovieEntity.builder()
                        .id(2)
                        .releaseYear("2022")
                        .url("https://www.google.com/test_ima.png")
                        .name("test movie 23")
                        .ranking(55)
                        .noFavorites(45)
                        .classification("+18")
                        .category("Comedy")
                        .build(),
                MovieEntity.builder()
                        .id(2)
                        .releaseYear("2022")
                        .url("https://www.google.com/test_ima.png")
                        .name("test movie 23")
                        .ranking(55)
                        .noFavorites(45)
                        .classification("+18")
                        .category("Comedy")
                        .build());

        movieEntity = MovieEntity.builder()
                .id(5)
                .releaseYear("2022")
                .url("https://www.google.com/test_ima.png")
                .name("test movie 23")
                .ranking(55)
                .noFavorites(45)
                .classification("+18")
                .category("Comedy")
                .build();
    }

    @Test
    public void testGetAllByReleaseYear() {
        new Expectations() {{
            movieRepository.findMovieByReleaseYear(anyString);
            result = Optional.of(movieEntityList);
        }};

        List<MovieDTO> movieDTOList = movieService.getAllByReleaseYear("2022");
        Assert.assertNotNull(movieDTOList);
    }

    @Test
    public void testGetAllByReleaseYearError() {
        new Expectations() {{
            movieRepository.findMovieByReleaseYear(anyString);
            result = Optional.empty();
        }};
        Assert.assertThrows(MovieException.class, () -> movieService.getAllByReleaseYear("2022"));
    }

    @Test
    public void testGetAllGroupedByReleaseYear() {
        new Expectations() {{
            movieRepository.findAll();
            result = movieEntityList;
        }};
        Map<String, List<MovieDTO>> movieGrouped = movieService.getAllGroupByReleaseYear();
        Assert.assertNotNull(movieGrouped);
    }

    @Test
    public void testGetAllGroupedByReleaseYearError() {
        new Expectations() {{
            movieRepository.findAll();
            result = new NullPointerException();
        }};
        Assert.assertThrows(MovieException.class, () -> movieService.getAllGroupByReleaseYear());
    }

    @Test
    public void testVoteMovie() {
        new Expectations() {{
            movieRepository.findById(anyInt);
            result = movieEntity;
            movieRepository.save((MovieEntity) any);
            result = any;
        }};

        MovieException movieException = null;
        try {
            movieService.voteMovie(5, true);
        }catch (MovieException e){
            movieException = e;
        }
        Assert.assertNull(movieException);
    }

    @Test
    public void getMovie() {
        new Expectations() {{
            movieRepository.findById(anyInt);
            result = movieEntity;
        }};
        Assert.assertNotNull(movieService.getMovie(5));
    }
}