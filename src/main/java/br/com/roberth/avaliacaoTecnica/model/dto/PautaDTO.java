package br.com.roberth.avaliacaoTecnica.model.dto;

import br.com.roberth.avaliacaoTecnica.model.entidades.Pauta;
import jakarta.validation.constraints.NotBlank;

public record PautaDTO(
        @NotBlank
        String assunto
) {
        public PautaDTO(Pauta pauta) {
                this(pauta.getAssunto());
        }
}
