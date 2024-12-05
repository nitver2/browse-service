package com.tbf.browse.exception;

public class MovieNotFound extends RuntimeException {

    MovieNotFound(String message){
        super(message);
    }
}
