package io.github.mateuussilvapb.app_jm_perfumaria.exceptions;

import io.github.mateuussilvapb.app_jm_perfumaria.infra.produto.exceptions.ProdutoNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProdutoNotFound(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }

    public record ErrorResponse(HttpStatus status, String error, String message,
                                LocalDateTime timestamp, Map<String, String> custom) {
        public ErrorResponse(HttpStatus status, String error, String message, LocalDateTime timestamp) {
            this(status, error, message, timestamp, Collections.emptyMap());
        }
    }
}
