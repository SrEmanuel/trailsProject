package dev.trailsgroup.trailsproject.resources.exceptions;

import dev.trailsgroup.trailsproject.services.exceptions.AuthorizationException;
import dev.trailsgroup.trailsproject.services.exceptions.DatabaseException;
import dev.trailsgroup.trailsproject.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> database(AuthorizationException e, HttpServletRequest request) {
        String error = "Access Denied";
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError err = new StandardError(status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<StandardError> missingPathVariableException(MissingPathVariableException e, HttpServletRequest request) {
        String error = "Access Denied";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ArgumentError>> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<ArgumentError> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String name = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.add(new ArgumentError(status.value(), name, message));
        });

        return ResponseEntity.status(status).body(errors);
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<StandardError> unknownHostException(UnknownHostException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        StandardError err = new StandardError(status.value(), "Não foi possível conectar aos servidores. Tente novamente mais tarde.", request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }



}