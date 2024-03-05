package com.alviter.movies.domain.crosscutting;

import java.time.LocalDateTime;

public class Utils {

    public static LocalDateTime getDateTime(){
        return LocalDateTime.now();
    }
}