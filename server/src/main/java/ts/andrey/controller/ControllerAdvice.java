package ts.andrey.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ts.andrey.exception.CoffeeServerException;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CoffeeServerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> processException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
