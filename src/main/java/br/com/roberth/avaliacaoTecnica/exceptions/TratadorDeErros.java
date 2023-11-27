package br.com.roberth.avaliacaoTecnica.exceptions;

import br.com.roberth.avaliacaoTecnica.model.dto.DadosErroValidacaoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collection;

@RestControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(java.lang.IllegalStateException.class)
    public ResponseEntity tratarErroIllegalStateException(java.lang.IllegalStateException ex) {
        if (ex.getCause() instanceof PautaNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Content-Type", "application/json")
                    .body(new DadosErroValidacaoDTO(null, ex.getMessage().split(": ")[1]));
        }
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(PautaConflictException.class)
    public ResponseEntity tratarErroPautaConflictException(PautaConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .header("Content-Type", "application/json")
                .body(new DadosErroValidacaoDTO(null, ex.getMessage()));
    }

    @ExceptionHandler(PautaBadRequestException.class)
    public ResponseEntity tratarErroPautaBadRequestException(PautaBadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(new DadosErroValidacaoDTO(null, ex.getMessage()));
    }

    @ExceptionHandler(PautaForbbidenExeception.class)
    public ResponseEntity tratarErroPautaForbbidenExeception(PautaForbbidenExeception ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .header("Content-Type", "application/json")
                .body(new DadosErroValidacaoDTO(null, ex.getMessage()));
    }

    @ExceptionHandler(PautaNotFoundException.class)
    public ResponseEntity tratarErroPautaNotFoundException(PautaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header("Content-Type", "application/json")
                .body(new DadosErroValidacaoDTO(null, ex.getMessage()));
    }

    @ExceptionHandler(java.lang.IllegalArgumentException.class)
    public ResponseEntity tratarErroIllegalArgumentException(java.lang.IllegalArgumentException ex) {
        String message = "";
        if (ex.getMessage().toLowerCase().contains("enumrespostavotacao")) {
            message = "Opção inválida para votação!";
        } else {
            message = ex.getMessage();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(new DadosErroValidacaoDTO(null, message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErroIllegalArgumentException(MethodArgumentNotValidException ex) {
        Collection<FieldError> erros = ex.getFieldErrors();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/json")
                .body(erros.stream().map(DadosErroValidacaoDTO::new).toList());
    }

}
