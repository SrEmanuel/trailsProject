package dev.trailsgroup.trailsproject.resources.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


//All Exception handling  code is based on https://github.com/acenelio/workshop-springboot2-jpa/tree/master/src/main/java/com/educandoweb/course/resources/exceptions
@ControllerAdvice
public class ArgumentExpectionHandler {

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

}