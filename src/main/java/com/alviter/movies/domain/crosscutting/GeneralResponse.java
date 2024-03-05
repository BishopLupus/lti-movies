package com.alviter.movies.domain.crosscutting;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class GeneralResponse<T> {

    private T data;
    private LocalDateTime localDateTime;
    private String message;
    private int responseCode;
}