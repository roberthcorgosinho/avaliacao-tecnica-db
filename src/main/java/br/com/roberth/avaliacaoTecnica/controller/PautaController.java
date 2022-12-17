package br.com.roberth.avaliacaoTecnica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.roberth.avaliacaoTecnica.model.Pauta;
import br.com.roberth.avaliacaoTecnica.repository.PautaRepository;

@RestController
@RequestMapping("/pautas")
public class PautaController {
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@GetMapping
	public List<Pauta> listarTodos() {
		return pautaRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pauta adicionar(@RequestBody Pauta pauta) {
		return pautaRepository.save(pauta);
	}

}
