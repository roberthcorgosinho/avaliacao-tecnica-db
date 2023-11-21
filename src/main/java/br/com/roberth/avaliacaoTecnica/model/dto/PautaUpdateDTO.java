package br.com.roberth.avaliacaoTecnica.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PautaUpdateDTO(
        @NotNull
        Long id,
        @NotBlank
        String assunto,
        SessaoVotacaoUpdateDTO sessaoVotacaoDTO
) { }
