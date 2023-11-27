package br.com.roberth.avaliacaoTecnica.controller;

import br.com.roberth.avaliacaoTecnica.model.dto.DadosErroValidacaoDTO;
import br.com.roberth.avaliacaoTecnica.model.dto.SessaoVotacaoDadosDTO;
import br.com.roberth.avaliacaoTecnica.model.dto.VotoDadosDTO;
import br.com.roberth.avaliacaoTecnica.services.SessaoVotacaoService;
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
@RequestMapping("/sessoes-votacao")
@Tag(description = "Gestão das sessões de votação das pautas cadastradas", name = "Sessões de Votação")
public class SessaoVotacaoController {

    @Autowired
    private SessaoVotacaoService sessaoVotacaoService;

    /**
     * adicionarSessaoVotacao
     *
     * Adiciona uma sessao de votacao a uma pauta
     *
     *
     * @param sessao
     * @return ResponseEntity: sessao criada
     * @throws Exception
     */
    @PostMapping
    @Transactional
    @Operation(summary = "Abrir sessão de votação", description = "Abre uma sessão de votação em uma pauta específica")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Voto computado",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = SessaoVotacaoDadosDTO.class
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404", description = "A pauta informada não existe",
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
                    responseCode = "409", description = "Já existe uma sessão de votação para a Pauta",
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
                    responseCode = "400", description = "Ocorreu um erro inesperado",
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
    public ResponseEntity adicionarSessaoVotacao(@RequestBody @Valid SessaoVotacaoDadosDTO sessao) throws Exception {
        SessaoVotacaoDadosDTO sessaoCriada = sessaoVotacaoService.adicionarSessao(sessao);
        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoCriada);
    }

}
