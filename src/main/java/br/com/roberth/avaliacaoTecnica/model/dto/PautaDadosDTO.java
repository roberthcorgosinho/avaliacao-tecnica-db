package br.com.roberth.avaliacaoTecnica.model.dto;

import br.com.roberth.avaliacaoTecnica.model.entidades.Pauta;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PautaDadosDTO(
        Long id,
        @NotBlank
        String assunto,
        Long idSessaoVotacao
) {
    public PautaDadosDTO(Pauta obj) {
       this(obj.getId(),
               obj.getAssunto(),
               (obj.getSessaoVotacao() != null ? obj.getSessaoVotacao().getId() : null)
       );
    }
}
