package com.alviter.movies.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false, name = "nofavorites")
    private long noFavorites;
    @Column(nullable = false, name = "releaseyear")
    private String releaseYear;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false, name = "movieclass")
    private String classification;
    @Column(nullable = false)
    private Integer ranking;
}