package com.alviter.movies.domain.converter;

import com.alviter.movies.domain.dto.MovieDTO;
import com.alviter.movies.repository.entity.MovieEntity;
import mockit.Expectations;
import mockit.Tested;
import org.junit.Assert;
import org.junit.Test;

public class MovieToMovieDTOConverterTest {

    @Tested
    private MovieToMovieDTOConverter movieToMovieDTOConverter;

    private static MovieEntity movieEntity = MovieEntity.builder()
            .id(54)
            .url("https://www.google.com/image.png")
            .name("Movie test 1")
            .category("action")
            .classification("E+")
            .noFavorites(54)
            .ranking(2)
            .releaseYear("2024")
            .build();

    public static MovieDTO movieDTO = MovieDTO.builder()
            .id(54)
            .url("https://www.google.com/image.png")
            .name("Movie test 1")
            .category("action")
            .classification("E+")
            .noFavorites(54)
            .ranking(2)
            .releaseYear("2024")
            .build();

    @Test
    public void testConvertOk(){
        new Expectations(){{
            movieToMovieDTOConverter.apply(movieEntity);
            result = movieDTO;
        }};

        MovieDTO movieDTO1 =  movieToMovieDTOConverter.apply(movieEntity);
        Assert.assertEquals(movieDTO1, movieDTO);
    }

    @Test
    public void testConvertWithError(){
        Assert.assertThrows(NullPointerException.class, () -> movieToMovieDTOConverter.apply(null));
    }
}