package dev.trailsgroup.trailsproject.resources.exceptions;


import dev.trailsgroup.trailsproject.resources.utils.StandardError;
import dev.trailsgroup.trailsproject.services.exceptions.ClientUploadException;
import dev.trailsgroup.trailsproject.services.exceptions.UploadException;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;


@ControllerAdvice
public class ContentExceptionHandler {

    @ExceptionHandler(InvalidContentTypeException.class)
    public ResponseEntity<StandardError> database(InvalidContentTypeException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UploadException.class)
    public ResponseEntity<StandardError> database(UploadException e, HttpServletRequest request) {
        String error = "Upload error";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError err = new StandardError(status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }


    @ExceptionHandler(ClientUploadException.class)
    public ResponseEntity<StandardError> database(ClientUploadException e, HttpServletRequest request) {
        String error = "Upload error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<StandardError> database(SizeLimitExceededException e, HttpServletRequest request) {
        String error = "A requisição foi rejeitada pois o tamanho do arquivo ("+ e.getActualSize()+" bytes) excede o limite permitido de ("+ e.getPermittedSize()+" bytes)";
        HttpStatus status = HttpStatus.PAYLOAD_TOO_LARGE;
        StandardError err = new StandardError(status.value(), error, request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }





}