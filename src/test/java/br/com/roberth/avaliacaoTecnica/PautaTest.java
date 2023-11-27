package br.com.roberth.avaliacaoTecnica;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.roberth.avaliacaoTecnica.model.entidades.Pauta;
import br.com.roberth.avaliacaoTecnica.model.entidades.SessaoVotacao;
import br.com.roberth.avaliacaoTecnica.model.entidades.Voto;
import br.com.roberth.avaliacaoTecnica.repository.PautaRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PautaTest {
	
	@Autowired
    private TestRestTemplate testRestTemplate;
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private PautaRepository repository;
    
    private Pauta pauta;
    private SessaoVotacao sessaoVotacao;
    private Voto voto;
    
    @BeforeAll
    public void iniciar() {
        this.pauta = new Pauta();        
        this.pauta.setAssunto("Teste Automatizado");
    }
    
    @Test
    public void listarTodasPautasTest() throws Exception {
    	this.mockMvc.perform(get("/pautas")).andExpect(status().isOk());
    }
    
    @Test
    public void criarNovaPautaTest() {
    	HttpEntity<Pauta> httpEntity = new HttpEntity<>(this.pauta);    	
    	ResponseEntity<Pauta> response = this.testRestTemplate
            .exchange("/pautas", HttpMethod.POST, httpEntity, Pauta.class);    
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody().getAssunto(), "Teste Automatizado");
    }
    
    @Test
    public void criarNovaPautaInvalidaTest() {    	
    	Pauta pautaAux = new Pauta();
    	HttpEntity<Pauta> httpEntity = new HttpEntity<>(pautaAux);    	
    	ResponseEntity<Pauta> response = this.testRestTemplate
            .exchange("/pautas", HttpMethod.POST, httpEntity, Pauta.class);    
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
    
    @Test
    public void obterPautaInvalidaTest() throws Exception {
    	this.mockMvc.perform(get("/pautas/{id}", 0)).andExpect(status().isNotFound());
    }
    
    @Test
    public void obterPautaTest() throws Exception {
    	this.pauta = repository.save(this.pauta);
    	this.mockMvc.perform(get("/pautas/{id}", this.pauta.getId())).andExpect(status().isOk());
    }
    
    @Test
    public void obterResultadoVotacaoTest() throws Exception {
    	this.pauta = repository.save(this.pauta);
    	this.mockMvc.perform(get("/pautas/{id}/resultado-votacao", this.pauta.getId()))
    		.andExpect(status().isOk())
    		.andExpect(content().string(containsString("totalVotos")));
    }
    
    @Test
    public void abrirSessaoVotacao() {
    	HttpEntity<Pauta> httpEntity = new HttpEntity<>(this.pauta);    	
    	ResponseEntity<Pauta> response = this.testRestTemplate
            .exchange("/pautas", HttpMethod.POST, httpEntity, Pauta.class);
    	
    	SessaoVotacao sessao = new SessaoVotacao();
    	sessao.setDuracaoEmMinutos(5);
    	HttpEntity<SessaoVotacao> httpEntitySessao = new HttpEntity<>(sessao);
    	ResponseEntity<Pauta> responseSessao = this.testRestTemplate
                .exchange("/pautas/"+response.getBody().getId()+"/abrir-votacao", HttpMethod.POST, httpEntitySessao, Pauta.class);
    	
        assertEquals(responseSessao.getStatusCode(), HttpStatus.CREATED);
        assertEquals(responseSessao.getBody().getAssunto(), "Teste Automatizado");
        assertEquals(responseSessao.getBody().getSessaoVotacao().getDuracaoEmMinutos(), 5);
    }
    
    @Test
    public void abrirSessaoVotacaoConflict() {
    	HttpEntity<Pauta> httpEntity = new HttpEntity<>(this.pauta);    	
    	ResponseEntity<Pauta> response = this.testRestTemplate
            .exchange("/pautas", HttpMethod.POST, httpEntity, Pauta.class);
    	
    	SessaoVotacao sessao = new SessaoVotacao();
    	sessao.setDuracaoEmMinutos(5);
    	HttpEntity<SessaoVotacao> httpEntitySessao = new HttpEntity<>(sessao);
    	ResponseEntity<Pauta> responseSessao = this.testRestTemplate
                .exchange("/pautas/"+response.getBody().getId()+"/abrir-votacao", HttpMethod.POST, httpEntitySessao, Pauta.class);
    	
    	ResponseEntity<Pauta> responseSessao2 = this.testRestTemplate
                .exchange("/pautas/"+response.getBody().getId()+"/abrir-votacao", HttpMethod.POST, httpEntitySessao, Pauta.class);
    	
        assertEquals(responseSessao.getStatusCode(), HttpStatus.CREATED);
        assertEquals(responseSessao.getBody().getAssunto(), "Teste Automatizado");
        assertEquals(responseSessao.getBody().getSessaoVotacao().getDuracaoEmMinutos(), 5);
        assertEquals(responseSessao2.getStatusCode(), HttpStatus.CONFLICT);
    }
    
    @Test
    public void abrirSessaoVotacaoNotFound() {
    	SessaoVotacao sessao = new SessaoVotacao();
    	sessao.setDuracaoEmMinutos(5);
    	HttpEntity<SessaoVotacao> httpEntitySessao = new HttpEntity<>(sessao);
    	ResponseEntity<Pauta> responseSessao = this.testRestTemplate
                .exchange("/pautas/0/abrir-votacao", HttpMethod.POST, httpEntitySessao, Pauta.class);
    	
        assertEquals(responseSessao.getStatusCode(), HttpStatus.NOT_FOUND);
    }
    
}
