package br.com.roberth.avaliacaoTecnica.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.roberth.avaliacaoTecnica.enums.EnumRespostaVotacao;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaBadRequestException;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaConflictException;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaForbbidenExeception;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaNotFoundException;
import br.com.roberth.avaliacaoTecnica.model.dto.ResultadoVotacaoDTO;
import br.com.roberth.avaliacaoTecnica.model.entidades.Pauta;
import br.com.roberth.avaliacaoTecnica.model.entidades.SessaoVotacao;
import br.com.roberth.avaliacaoTecnica.model.entidades.Voto;
import br.com.roberth.avaliacaoTecnica.services.PautaService;
import br.com.roberth.avaliacaoTecnica.services.SessaoVotacaoService;
import br.com.roberth.avaliacaoTecnica.services.VotoService;

@RestController
@RequestMapping("/pautas")
public class PautaController {
	
	@Autowired
	private PautaService pautaService;
	
	@Autowired
	private VotoService votoService;
	
	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;
	
	@GetMapping
	public List<Pauta> listarTodos() throws Exception {
		return pautaService.listarPautas();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pauta adicionar(@RequestBody Pauta pauta) throws Exception {
		return pautaService.adicionarPauta(pauta);
	}
	
	@GetMapping("/{id}")
	public Pauta obterPauta(@PathVariable Long id) throws Exception {
		return pautaService.obterPauta(id);
	}
	
	@GetMapping("/{id}/resultado-votacao")
	public ResultadoVotacaoDTO obterResultadoVotacaoPauta(@PathVariable Long id) throws Exception {		
		return pautaService.resultadoVotacaoPauta(id);
	}	
	
	@PostMapping("/{id}/abrir-votacao")
	@ResponseStatus(HttpStatus.CREATED)
	public Pauta adicionarSessaoVotacao(@PathVariable Long id, @RequestBody SessaoVotacao sessao) throws Exception {
		Pauta pauta = pautaService.obterPauta(id);
		sessao.setPauta(pauta);
		sessao = sessaoVotacaoService.adicionarSessao(sessao);
		pauta.setSessaoVotacao(sessao);
		return pautaService.atualizarPauta(pauta);
	}
	
	@PostMapping("/{id}/votar")
	@ResponseStatus(HttpStatus.CREATED)
	public Pauta votar(@PathVariable Long id, @RequestBody Voto voto) throws Exception {
		Pauta pauta = pautaService.obterPauta(id);
		if (sessaoVotacaoService.isSessaoVotacaoAberta(pauta.getSessaoVotacao())) {
			if (sessaoVotacaoService.podeVotar(voto.getId().getCpf())) {				
				voto.setSessaoVotacao(pauta.getSessaoVotacao());
				voto.getId().setIdSessaoVotacao(pauta.getSessaoVotacao().getId());
				if (votoService.existeVoto(voto)) {
					throw new PautaConflictException("O usuário " + voto.getId().getCpf() + " já votou na Pauta " + id);
				} else if (
					voto.getOpcao().equalsIgnoreCase(EnumRespostaVotacao.SIM.getDescricao()) ||
					voto.getOpcao().equalsIgnoreCase(EnumRespostaVotacao.NAO.getDescricao())	
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
				throw new PautaForbbidenExeception("O usuário " + voto.getId().getCpf() + " não está autorizado a votar na pauta " + id);
			}
		} else {
			throw new PautaNotFoundException("Não existe sessão de votação aberta para a pauta " + id);
		}
		return pautaService.atualizarPauta(pauta);
	}
	
	

}
