package br.com.roberth.avaliacaoTecnica.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResultadoVotacaoDTO(
        Long idPauta,
        String assuntoPauta,
        Long totalVotos,
        Long totalVotosSim,
        Long totalVotosNao
) {
}
