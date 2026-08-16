package com.lucas.salesapi.exception;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> details = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(this::formatFieldError)
            .collect(Collectors.toList());

        ApiErrorResponse response = buildErrorResponse(
            HttpStatus.BAD_REQUEST,
            "Erro de validacao.",
            ((ServletWebRequest) request).getRequest().getRequestURI(),
            details
        );

        return ResponseEntity.badRequest().body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        MissingServletRequestParameterException exception,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        ApiErrorResponse response = buildErrorResponse(
            HttpStatus.BAD_REQUEST,
            "Parametro obrigatorio ausente: " + exception.getParameterName(),
            ((ServletWebRequest) request).getRequest().getRequestURI(),
            Collections.emptyList()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException exception,
                                                                    HttpServletRequest request) {
        ApiErrorResponse response = buildErrorResponse(
            HttpStatus.BAD_REQUEST,
            exception.getMessage(),
            request.getRequestURI(),
            Collections.emptyList()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException exception,
                                                               HttpServletRequest request) {
        String parameterName = exception.getName();
        ApiErrorResponse response = buildErrorResponse(
            HttpStatus.BAD_REQUEST,
            "Parametro invalido: " + parameterName,
            request.getRequestURI(),
            Collections.singletonList("Verifique o formato informado para o parametro " + parameterName + ".")
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(Exception exception,
                                                                      HttpServletRequest request) {
        ApiErrorResponse response = buildErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Ocorreu um erro inesperado ao processar a requisicao.",
            request.getRequestURI(),
            Collections.emptyList()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private ApiErrorResponse buildErrorResponse(HttpStatus status, String message, String path, List<String> details) {
        return new ApiErrorResponse(
            LocalDateTime.now(),
            status.value(),
            status.getReasonPhrase(),
            message,
            path,
            details
        );
    }

    private String formatFieldError(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }
}
