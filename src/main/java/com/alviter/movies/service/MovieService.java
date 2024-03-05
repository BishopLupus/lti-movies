package com.alviter.movies.service;

import com.alviter.movies.domain.dto.MovieDTO;

import java.util.List;
import java.util.Map;

public interface MovieService {

    List<MovieDTO> getAllByReleaseYear(String releaseYear);
    Map<String, List<MovieDTO>> getAllGroupByReleaseYear();
    void voteMovie(int id, boolean isUp);

    MovieDTO getMovie(int id);
}