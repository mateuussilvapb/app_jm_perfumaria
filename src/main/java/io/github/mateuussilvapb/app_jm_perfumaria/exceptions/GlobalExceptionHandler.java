package io.github.mateuussilvapb.app_jm_perfumaria.exceptions;

import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.exceptions.CategoriaInUseException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.exceptions.CategoriaNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.categoria.exceptions.CategoriaSameNameException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.exceptions.PrecoUnitarioInvalidoException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.common.movimentacaoEstoque.exceptions.ValorDescontoInvalidoException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.entradaEstoque.exceptions.EntradaEstoqueSemProdutosException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.exceptions.MarcaInUseException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.exceptions.MarcaNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.marca.exceptions.MarcaSameNameException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produto.exceptions.*;
import io.github.mateuussilvapb.app_jm_perfumaria.application.produtoEntradaEstoque.exceptions.ProdutoEntradaEstoqueNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.application.saidaEstoque.exceptions.SaidaEstoqueNotFoundException;
import io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions.IncorrectCurrentPasswordException;
import io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions.SelfToggleStatusException;
import io.github.mateuussilvapb.app_jm_perfumaria.auth.keycloak.user.exceptions.ToggleUserStatusException;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProdutoSameNameException.class)
    public ResponseEntity<ErrorResponse> handleProdutoSameName(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProdutoEmCadastramentoException.class)
    public ResponseEntity<ErrorResponse> handleProdutoEmCadastramento(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoriaInUseException.class)
    public ResponseEntity<ErrorResponse> handleCategoriaInUseException(Exception ex) {
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MarcaInUseException.class)
    public ResponseEntity<ErrorResponse> handleMarcaInUseException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntradaEstoqueSemProdutosException.class)
    public ResponseEntity<ErrorResponse> handleEntradaEstoqueSemProduto(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProdutoEntradaEstoqueNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProdutoEntradaEstoqueNotFound(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EstoqueProdutoInsuficienteException.class)
    public ResponseEntity<ErrorResponse> handleEstoqueInsuficiente(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(QuantidadeMovimentacaoEstoqueInvalidaException.class)
    public ResponseEntity<ErrorResponse> handleQuantidadeEntradaEstoqueInvalida(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SaidaEstoqueNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSaidaEstoqueNotFound(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PrecoUnitarioInvalidoException.class)
    public ResponseEntity<ErrorResponse> handlePrecoUnitarioInvalido(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValorDescontoInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleValorDescontoInvalido(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IncorrectCurrentPasswordException.class)
    public ResponseEntity<ErrorResponse> handleIncorrectCurrentPassword(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ToggleUserStatusException.class)
    public ResponseEntity<ErrorResponse> handleToggleUserStatusError(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getClass().getName(), ex.getMessage(), LocalDateTime.now()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SelfToggleStatusException.class)
    public ResponseEntity<ErrorResponse> handleSelfToggleStatusException(Exception ex) {
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
