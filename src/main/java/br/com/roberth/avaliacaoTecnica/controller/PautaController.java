package br.com.roberth.avaliacaoTecnica.controller;

import br.com.roberth.avaliacaoTecnica.enums.EnumRespostaVotacao;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaBadRequestException;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaConflictException;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaForbbidenExeception;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaNotFoundException;
import br.com.roberth.avaliacaoTecnica.model.dto.*;
import br.com.roberth.avaliacaoTecnica.model.entidades.Pauta;
import br.com.roberth.avaliacaoTecnica.model.entidades.SessaoVotacao;
import br.com.roberth.avaliacaoTecnica.model.entidades.Voto;
import br.com.roberth.avaliacaoTecnica.model.entidades.VotoId;
import br.com.roberth.avaliacaoTecnica.services.PautaService;
import br.com.roberth.avaliacaoTecnica.services.SessaoVotacaoService;
import br.com.roberth.avaliacaoTecnica.services.VotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/pautas")
public class PautaController {
	
	@Autowired
	private PautaService pautaService;
	
	@Autowired
	private VotoService votoService;
	
	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;

	@PostMapping("/adicionarEmMassa")
	@Transactional
	public ResponseEntity adicionarPautaEmLote(@RequestBody @Valid Collection<PautaDTO> pautas) throws Exception {
		try {
			Collection<Pauta> pautasCriadas = new HashSet<>();
			for (PautaDTO pautaDTO : pautas) {
				pautasCriadas.add(pautaService.adicionarPauta(new Pauta(pautaDTO)));
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(pautasCriadas.stream().map(PautaDTO::new));
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@PostMapping
	@Transactional
	public ResponseEntity adicionarPauta(@RequestBody @Valid PautaDTO pauta, UriComponentsBuilder uriBuilder) throws Exception {
		try {
			Pauta pautaCriada = pautaService.adicionarPauta(new Pauta(pauta));
			URI uri = uriBuilder.path("/pautas/{id}").buildAndExpand(pautaCriada.getId()).toUri();
			return ResponseEntity.created(uri).body(new PautaDTO(pautaCriada));
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@PutMapping
	@Transactional
	public ResponseEntity atualizarPauta(@RequestBody @Valid PautaUpdateDTO pauta) throws Exception {
		try {
			Pauta pautaAtualizada = pautaService.atualizarPauta(pauta);
			return ResponseEntity.status(HttpStatus.OK).body(pautaAtualizada);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@GetMapping
	public ResponseEntity listarTodos(@PageableDefault(size = 10, sort = {"assunto"}) Pageable paginacao) throws Exception {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(pautaService.listarPautas(paginacao));
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity obterPautaPorId(@PathVariable Long id) throws Exception {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(pautaService.obterPauta(id));
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@GetMapping("/{id}/resultado-votacao")
	public ResponseEntity obterResultadoVotacaoPauta(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(pautaService.resultadoVotacaoPauta(id));
	}	
	
	@PostMapping("/{id}/abrir-votacao")
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public ResponseEntity adicionarSessaoVotacao(@PathVariable Long id, @RequestBody SessaoVotacaoDTO sessao) throws Exception {
		Pauta pauta = pautaService.obterPauta(id);
		SessaoVotacao sessaoVotacao = new SessaoVotacao(sessao);
		sessaoVotacao.setPauta(pauta);
		sessaoVotacao = sessaoVotacaoService.adicionarSessao(sessaoVotacao);
		pauta.setSessaoVotacao(sessaoVotacao);
		return ResponseEntity.ok(pautaService.atualizarPauta(pauta));
	}
	
	@PostMapping("/{id}/votar")
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public ResponseEntity votar(@PathVariable Long id, @RequestBody @Valid VotoDTO votoDTO) throws Exception {
		Pauta pauta = pautaService.obterPauta(id);
		if (pauta.getSessaoVotacao() == null) {
			throw new PautaNotFoundException("Não existe sessão de votação aberta");
		}
		if (sessaoVotacaoService.isSessaoVotacaoAberta(pauta.getSessaoVotacao())) {
			if (sessaoVotacaoService.podeVotar(votoDTO.cpf())) {
				Voto voto = new Voto();
				voto.setId(new VotoId(votoDTO.cpf(), pauta.getSessaoVotacao().getId()));
				voto.setSessaoVotacao(pauta.getSessaoVotacao());
				voto.setOpcao(EnumRespostaVotacao.valueOf(votoDTO.opcao()));
				if (votoService.existeVoto(voto)) {
					throw new PautaConflictException("O usuário " + voto.getId().getCpf() + " já votou na Pauta " + id);
				} else if (
					voto.getOpcao().getDescricao().equalsIgnoreCase(EnumRespostaVotacao.NAO.getDescricao()) ||
					voto.getOpcao().getDescricao().equalsIgnoreCase(EnumRespostaVotacao.SIM.getDescricao())
				) {
					voto = votoService.adicionarVoto(voto);
					if (pauta.getSessaoVotacao().getVotos() == null) {
						pauta.getSessaoVotacao().setVotos(new ArrayList<>());
					}
					pauta.getSessaoVotacao().getVotos().add(voto);
				} else {
					throw new PautaBadRequestException("Opção de votação inválida");
				}
			} else {
				throw new PautaForbbidenExeception("O usuário " + votoDTO.cpf() + " não está autorizado a votar na pauta " + id);
			}
		} else {
			throw new PautaNotFoundException("Não existe sessão de votação aberta para a pauta " + id);
		}
		return ResponseEntity.ok(pautaService.atualizarPauta(pauta));
	}

}
