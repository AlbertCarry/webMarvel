package com.marvel.config;

//import com.marvel.controller.CharactersController;
import com.marvel.model.Characters;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResponseConfig {
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public static class Char extends RuntimeException { }
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public static class BadRequest extends RuntimeException { }
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public static class Comic extends RuntimeException { }

}
