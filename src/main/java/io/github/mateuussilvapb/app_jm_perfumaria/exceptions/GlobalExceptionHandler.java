package io.github.mateuussilvapb.app_jm_perfumaria.exceptions;

import io.github.mateuussilvapb.app_jm_perfumaria.infra.categoria.exceptions.CategoriaNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.categoria.exceptions.CategoriaSameNameException;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.marca.exceptions.MarcaNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.marca.exceptions.MarcaSameNameException;
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoriaNotFound(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoriaSameNameException.class)
    public ResponseEntity<ErrorResponse> handleCategoriaSameName(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MarcaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMarcaNotFound(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MarcaSameNameException.class)
    public ResponseEntity<ErrorResponse> handleCategoriaMarcaName(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    public record ErrorResponse(HttpStatus status, String error, String message,
                                LocalDateTime timestamp, Map<String, String> custom) {
        public ErrorResponse(HttpStatus status, String error, String message, LocalDateTime timestamp) {
            this(status, error, message, timestamp, Collections.emptyMap());
        }
    }
}
