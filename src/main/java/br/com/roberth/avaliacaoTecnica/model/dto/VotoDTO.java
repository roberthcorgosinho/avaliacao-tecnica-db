package br.com.roberth.avaliacaoTecnica.model.dto;

import br.com.roberth.avaliacaoTecnica.enums.EnumRespostaVotacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VotoDTO(
        @NotBlank
        String cpf,
        @NotNull
        String opcao
) {
}
