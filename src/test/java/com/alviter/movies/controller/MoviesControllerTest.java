package com.alviter.movies.controller;

import com.alviter.movies.domain.dto.MovieDTO;
import com.alviter.movies.service.MovieService;
import jakarta.servlet.ServletException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MoviesControllerTest {

    private MockMvc mockMvc;
    @Mock
    private MovieService movieService;
    @InjectMocks
    private MoviesController moviesController;
    private int id;
    private String releaseYear;
    private MovieDTO movieDTO;
    private List<MovieDTO> movieDTOList;
    public static final String MOVIE_PATH = "/v1/api/movie/";

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(moviesController).build();

        id = 2;
        releaseYear = "2023";
        movieDTO = MovieDTO.builder()
                .releaseYear("2023")
                .noFavorites(5)
                .url("www.test.com/image-fake.png")
                .name("Test movie")
                .classification("E+")
                .category("Test movie")
                .id(55)
                .ranking(4)
                .build();
        int count = 1;
        movieDTOList = new ArrayList<>(20);
        while (count < 20) {
            movieDTOList.add(MovieDTO.builder()
                    .id(count)
                    .name("test movie " + count)
                    .ranking(count + 2)
                    .url("https://www.google.com/test_image_" + count + ".jpg")
                    .releaseYear("2023")
                    .build());
            count++;
        }
    }

    @Test
    public void testGetAllReleaseYear() throws Exception {
        when(movieService.getAllByReleaseYear(anyString())).thenReturn(movieDTOList);
        MvcResult result = mockMvc.perform(get(MOVIE_PATH + "all/{release-year}", releaseYear))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void testGetAllGroupedByReleaseYear() throws Exception {
        when(movieService.getAllGroupByReleaseYear()).thenReturn(movieDTOList.stream()
                .collect(groupingBy(MovieDTO::getReleaseYear)));

        MvcResult result = mockMvc.perform(get(MOVIE_PATH + "all-grouped"))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void testVoteUpMovie() throws Exception {

        MvcResult result = mockMvc.perform(post(MOVIE_PATH + "/vote-down/{id}", id))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void testVoteDownMovie() throws Exception {
        MvcResult result = mockMvc.perform(post(MOVIE_PATH + "/vote-up/{id}", id))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void testGetMovieOk() throws Exception {
        when(movieService.getMovie(anyInt())).thenReturn(movieDTO);
        MvcResult result = mockMvc.perform(get(MOVIE_PATH + "get-movie/{id}", id)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void testGetMovieException() {
        when(movieService.getMovie(anyInt())).thenThrow(new NullPointerException("NullPointerException"));
        Assertions.assertThrows(ServletException.class, () -> mockMvc.perform(get(
                        MOVIE_PATH + "get-movie/{id}", id))
                .andExpect(status().is(500))
                .andReturn());
    }
}