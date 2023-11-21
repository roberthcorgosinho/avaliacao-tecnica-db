package br.com.roberth.avaliacaoTecnica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(java.lang.IllegalStateException.class)
    public ResponseEntity tratarErroIllegalStateException(java.lang.IllegalStateException ex) {
        if (ex.getCause() instanceof PautaNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage().split(": ")[1]);
        }
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(PautaConflictException.class)
    public ResponseEntity tratarErroPautaConflictException(PautaConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(PautaBadRequestException.class)
    public ResponseEntity tratarErroPautaBadRequestException(PautaBadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(PautaForbbidenExeception.class)
    public ResponseEntity tratarErroPautaForbbidenExeception(PautaForbbidenExeception ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(PautaNotFoundException.class)
    public ResponseEntity tratarErroPautaNotFoundException(PautaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(java.lang.IllegalArgumentException.class)
    public ResponseEntity tratarErroIllegalArgumentException(java.lang.IllegalArgumentException ex) {
        String message = "";
        if (ex.getMessage().toLowerCase().contains("enumrespostavotacao")) {
            message = "Opção inválida para votação!";
        } else {
            message = ex.getMessage();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
