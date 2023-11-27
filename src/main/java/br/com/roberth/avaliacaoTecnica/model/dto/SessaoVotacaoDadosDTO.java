package br.com.roberth.avaliacaoTecnica.model.dto;

import br.com.roberth.avaliacaoTecnica.model.entidades.SessaoVotacao;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SessaoVotacaoDadosDTO(
        @NotNull
        Long idPauta,
        Integer duracaoEmMinutos,
        Long idSessaoVotacao
) {
    public SessaoVotacaoDadosDTO(SessaoVotacao sessaoVotacao) {
        this(sessaoVotacao.getPauta().getId(), sessaoVotacao.getDuracaoEmMinutos(), sessaoVotacao.getId());
    }

    public SessaoVotacaoDadosDTO(PautaDadosDTO pauta) {
        this(pauta.id(), null, pauta.idSessaoVotacao());
    }
}
