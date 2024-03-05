package com.alviter.movies.exception;

import mockit.Tested;
import org.junit.Test;

public class MovieExceptionTest {

    @Tested
    private MovieException movieException;

    @Test
    public void testMovieException(){
        String message = "Test exception message";
        movieException = new MovieException();
        movieException = new MovieException(message, new NullPointerException());
        movieException = new MovieException(message);
        movieException = new MovieException(new NullPointerException());
        movieException = new MovieException(message, new MovieException(),  false, true);
    }
}