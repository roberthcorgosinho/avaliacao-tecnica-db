package br.com.roberth.avaliacaoTecnica.controller;

import br.com.roberth.avaliacaoTecnica.exceptions.PautaBadRequestException;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaNotFoundException;
import br.com.roberth.avaliacaoTecnica.model.dto.DadosErroValidacaoDTO;
import br.com.roberth.avaliacaoTecnica.model.dto.PautaDadosDTO;
import br.com.roberth.avaliacaoTecnica.model.dto.ResultadoVotacaoDTO;
import br.com.roberth.avaliacaoTecnica.services.PautaService;
import br.com.roberth.avaliacaoTecnica.services.VotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/pautas")
@Tag(description = "Gestão das pautas cadastradas para votação", name = "Pautas")
public class PautaController {
	
	@Autowired
	private PautaService pautaService;

	@Autowired
	private VotoService votoService;

	/**
	 * adicionarPautaEmLote
	 *
	 * Endpoint que possibilita adicionar colecao de pautas simultaneamente
	 *
	 * @param pautas: A colecao de pautas a serem cadastradas
	 * @return ResponseEntity: A colecao de pautas criadas
	 * @throws PautaBadRequestException
	 */
	@PostMapping("/adicionar-em-massa")
	@Transactional
	@Operation(summary = "Adicionar em massa (lote)", description = "Possibilita adicionar coleção de pautas simultâneamente")
	public ResponseEntity adicionarPautaEmLote(@RequestBody @Valid Collection<PautaDadosDTO> pautas) throws PautaBadRequestException {
		Collection<PautaDadosDTO> pautasCriadas = new HashSet<>();
		for (PautaDadosDTO pautaDTO : pautas) {
			pautasCriadas.add(pautaService.adicionarPauta(pautaDTO));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(pautasCriadas);
	}

	/**
	 * adicionarPautaEmLote
	 *
	 * Endpoint que possibilita adicionar uma pauta
	 *
	 * @param pauta
	 * @param uriBuilder (injetado automaticamente pelo spring)
	 * @return ResponseEntity: Informacoes da pauta criada
	 * @throws PautaBadRequestException
	 */
	@PostMapping
	@Transactional
	@Operation(summary = "Adicionar pauta", description = "Adiciona uma pauta de votação")
	public ResponseEntity adicionarPauta(@RequestBody @Valid PautaDadosDTO pauta, UriComponentsBuilder uriBuilder) throws PautaBadRequestException {
		PautaDadosDTO pautaCriada = pautaService.adicionarPauta(pauta);
		URI uri = uriBuilder.path("/pautas/{idPauta}").buildAndExpand(pautaCriada.id()).toUri();
		return ResponseEntity.created(uri).body(pautaCriada);
	}

	/**
	 * listarTodas
	 *
	 * Endpoint que retorna uma colecao, paginada, contendo todas as pautas cadastradas
	 *
	 * @param paginacao: criterios para paginacao e ordenacao
	 * @return ResponseEntity: a lista paginada das pautas cadastradas
	 * @throws PautaNotFoundException
	 */
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200", description = "Pautas encontradas",
					content = {
							@Content(
									mediaType = "application/json",
									schema = @Schema(
											implementation = Page.class
									)
							)
					}
			),
			@ApiResponse(
					responseCode = "404", description = "Não existem pautas cadastradas",
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
	@GetMapping
	@Operation(summary = "Listagem de pautas", description = "Lista paginada de todas as pautas cadastradas")
	public ResponseEntity listarTodas(@ParameterObject @PageableDefault(size = 10, sort = {"assunto"}) Pageable paginacao) throws PautaNotFoundException {
		return ResponseEntity.status(HttpStatus.OK).body(pautaService.listarPautas(paginacao));
	}

	/**
	 * obterPautaPorId
	 *
	 * Endpoint que retorna uma pauta especifica atraves do seu ID
	 *
	 * @param idPauta
	 * @return
	 */
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200", description = "Pauta encontrada",
					content = {
							@Content(
									mediaType = "application/json",
									schema = @Schema(
											implementation = PautaDadosDTO.class
									)
							)
					}
			),
			@ApiResponse(
					responseCode = "404", description = "Não existem pautas cadastradas",
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
	@GetMapping("/{idPauta}")
	@Operation(summary = "Consulta de pauta", description = "Retorna uma pauta específica com base em seu ID")
	public ResponseEntity obterPautaPorId(@PathVariable Long idPauta) {
		return ResponseEntity.status(HttpStatus.OK).body(pautaService.obterPauta(idPauta));
	}

	/**
	 * totalizarVotos
	 *
	 * Endpoint que totaliza os votos realizados na sessao de votacao atribuida a pauta
	 *
	 * @param idPauta
	 * @return
	 */
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200", description = "Votos computados",
					content = {
							@Content(
									mediaType = "application/json",
									schema = @Schema(
											implementation = ResultadoVotacaoDTO.class
									)
							)
					}
			),
			@ApiResponse(
					responseCode = "404", description = "Não existem pautas cadastradas",
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
	@GetMapping("/totalizar-votos/{idPauta}")
	@Operation(summary = "Total de votos", description = "Informa a totalidade de votos de uma pauta específica com base em seu ID")
	public ResponseEntity totalizarVotos(@PathVariable Long idPauta) {
		return ResponseEntity.status(HttpStatus.OK).body(votoService.resultadoVotacao(idPauta));
	}

}
