package com.alviter.movies.controller;

import com.alviter.movies.domain.crosscutting.GeneralResponse;
import com.alviter.movies.domain.crosscutting.MovieConstants;
import com.alviter.movies.domain.crosscutting.Utils;
import com.alviter.movies.domain.dto.MovieDTO;
import com.alviter.movies.exception.MovieException;
import com.alviter.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/api/movie")
public class MoviesController {

    @Autowired
    private MovieService movieService;

    @GetMapping(value = "/all/{release-year}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GeneralResponse<List<MovieDTO>> getAllByReleaseYear(@PathVariable("release-year") String releaseYear) throws MovieException {
        return GeneralResponse.<List<MovieDTO>>builder()
                .data(movieService.getAllByReleaseYear(releaseYear))
                .localDateTime(Utils.getDateTime())
                .message(MovieConstants.SUCCESS)
                .responseCode(HttpStatus.OK.value())
                .build();
    }

    @GetMapping(value = "/all-grouped", produces = MediaType.APPLICATION_JSON_VALUE)
    public GeneralResponse<Map<String, List<MovieDTO>>> getAllGroupedByReleaseYear() throws MovieException {
        return GeneralResponse.<Map<String, List<MovieDTO>>>builder()
                .data(movieService.getAllGroupByReleaseYear())
                .localDateTime(Utils.getDateTime())
                .message(MovieConstants.SUCCESS)
                .responseCode(HttpStatus.OK.value())
                .build();
    }

    @PostMapping(value = "/vote-up/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GeneralResponse<String> voteUpMovie(@PathVariable int id) throws MovieException {
        movieService.voteMovie(id, true);
        return GeneralResponse.<String>builder()
                .data("Vote up process correctly.")
                .localDateTime(Utils.getDateTime())
                .responseCode(HttpStatus.OK.value())
                .message(MovieConstants.SUCCESS)
                .build();
    }

    @PostMapping(value = "/vote-down/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GeneralResponse<String> voteDownMovie(@PathVariable int id) throws MovieException {
        movieService.voteMovie(id, false);
        return GeneralResponse.<String>builder()
                .data("Vote down process correctly.")
                .localDateTime(Utils.getDateTime())
                .responseCode(HttpStatus.OK.value())
                .message(MovieConstants.SUCCESS)
                .build();
    }

    @GetMapping(value = "/get-movie/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GeneralResponse<MovieDTO> getMovie(@PathVariable int id) throws MovieException {
    return  GeneralResponse.<MovieDTO>builder()
                .data(movieService.getMovie(id))
                .message(MovieConstants.SUCCESS)
                .responseCode(HttpStatus.OK.value())
                .localDateTime(Utils.getDateTime())
                .build();
    }
}