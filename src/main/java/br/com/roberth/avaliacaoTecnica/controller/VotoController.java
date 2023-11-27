package br.com.roberth.avaliacaoTecnica.controller;

import br.com.roberth.avaliacaoTecnica.model.dto.DadosErroValidacaoDTO;
import br.com.roberth.avaliacaoTecnica.model.dto.ResultadoVotacaoDTO;
import br.com.roberth.avaliacaoTecnica.model.dto.VotoDadosDTO;
import br.com.roberth.avaliacaoTecnica.services.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/votos")
@Tag(description = "Gestão de votos de uma sessão de votação de uma pauta", name = "Votos")
public class VotoController {

    @Autowired
    private VotoService votoService;

    /**
     * adicionarVoto
     *
     * Endpoint que adiciona/computa um voto em uma sessao de votacao aberta de uma pauta
     *
     * @param voto
     * @return ResponseEntity: Voto computado
     * @throws Exception
     */
    @PostMapping
    @Transactional
    @Operation(summary = "Votar", description = "Computa um voto de um usuário específico em uma sessão de votação de uma pauta específica")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Voto computado",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = VotoDadosDTO.class
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404", description = "A pauta informada não existe / Não existe sessão de votação para a pauta",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = DadosErroValidacaoDTO.class
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "409", description = "Não existe sessão de votação aberta para a pauta / O usuário já votou na sessão de votação atual da pauta informada",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = DadosErroValidacaoDTO.class
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403", description = "O usuário não está autorizado a votar na sessão de votação atual da pauta informada",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = DadosErroValidacaoDTO.class
                                    )
                            )
                    }
            )
    })
    public ResponseEntity adicionarVoto(@RequestBody @Valid VotoDadosDTO voto) throws Exception {
        VotoDadosDTO votoAdicionado = votoService.adicionarVoto(voto);
        return ResponseEntity.status(HttpStatus.CREATED).body(votoAdicionado);
    }

}
