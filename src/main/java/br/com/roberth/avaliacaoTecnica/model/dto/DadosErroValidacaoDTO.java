package br.com.roberth.avaliacaoTecnica.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.FieldError;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DadosErroValidacaoDTO(String campo, String mensagem) {
    public DadosErroValidacaoDTO(FieldError erro) {
        this(erro.getField(), erro.getDefaultMessage());
    }
}
