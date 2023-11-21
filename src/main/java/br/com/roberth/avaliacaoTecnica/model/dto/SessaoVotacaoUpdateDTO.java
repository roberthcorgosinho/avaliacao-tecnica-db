package br.com.roberth.avaliacaoTecnica.model.dto;

import br.com.roberth.avaliacaoTecnica.model.entidades.SessaoVotacao;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public record SessaoVotacaoUpdateDTO(
        @NotNull
        Long id,
        Integer duracaoEmMinutos
) {
    public SessaoVotacaoUpdateDTO(SessaoVotacao sessaoVotacao) {
        this(sessaoVotacao.getId(), sessaoVotacao.getDuracaoEmMinutos());
    }
}
