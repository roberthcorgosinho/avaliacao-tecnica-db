package br.com.roberth.avaliacaoTecnica.model.dto;

import br.com.roberth.avaliacaoTecnica.model.entidades.Voto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VotoDadosDTO(
        @NotBlank
        String cpf,
        @NotNull
        Long idPauta,
        @NotBlank
        String opcao
) {
    public VotoDadosDTO(Voto voto) {
        this(voto.getId().getCpf(), voto.getSessaoVotacao().getPauta().getId(), voto.getOpcao().getDescricao());
    }
}
