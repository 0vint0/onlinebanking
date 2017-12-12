package com.vsvet.example.onlinebanking.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.Charset;


public final class RestResponses {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private final MediaType mediaType;

    private RestResponses(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public static RestResponses json() {
        return new RestResponses(new MediaType("application", "json", DEFAULT_CHARSET));
    }

    public <T> ResponseEntity<T> ok(T result) {
        return response(result, HttpStatus.OK);
    }

    public ResponseEntity<Void> ok() {
        return response(null, HttpStatus.OK);
    }

    public <T> ResponseEntity<T> accepted(T result) {
        return response(result, HttpStatus.ACCEPTED);
    }

    public <T> ResponseEntity<T> accepted() {
        return response(null, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Void> created() {
        return response(null, HttpStatus.CREATED);
    }

    public <T> ResponseEntity<T> created(T t) {
        return response(t, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> updated() {
        return response(null, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> deleted() {
        return response(null, HttpStatus.NO_CONTENT);
    }

    public <T> ResponseEntity<T> response(T result, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return new ResponseEntity<>(result, headers, status);
    }
}
