package br.com.zup.wagner.proposta.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandller {

    // capturando exceptions de validação

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validatiom(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationError error = new ValidationError(Instant.now(), status.value(), "Validation exception!",
                e.getMessage(), request.getRequestURI());

        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            error.addErro(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(error);
    }

    // capturar exceções de validação para campo unico

    @ExceptionHandler(ExceptionGenericValidation.class)
    public ResponseEntity<ValidationError> entityNotFound(ExceptionGenericValidation e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError error = new ValidationError(Instant.now(), status.value(), "Campo unico",
                e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }
}
