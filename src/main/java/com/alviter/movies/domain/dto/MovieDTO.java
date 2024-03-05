package com.alviter.movies.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MovieDTO {

    private Integer id;
    private String url;
    private long noFavorites;
    private String releaseYear;
    private String name;
    private String category;
    private String classification;
    private Integer ranking;
}