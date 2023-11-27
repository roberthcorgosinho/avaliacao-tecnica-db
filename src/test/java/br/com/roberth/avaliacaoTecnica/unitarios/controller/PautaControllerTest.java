package br.com.roberth.avaliacaoTecnica.unitarios.controller;

import br.com.roberth.avaliacaoTecnica.exceptions.PautaNotFoundException;
import br.com.roberth.avaliacaoTecnica.model.dto.PautaDadosDTO;
import br.com.roberth.avaliacaoTecnica.services.PautaService;
import br.com.roberth.avaliacaoTecnica.services.VotoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class PautaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<PautaDadosDTO> pautaDadosJson;

    @MockBean
    private VotoService votoService;

    @Test
    //@WithMockUser
    @DisplayName("Listar todas as pautas sem pauta cadastrada")
    void listarTodasPautasSemPautaCadastradaErro404() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/pautas")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Tentar listar uma pauta que nao existe")
    void listarPautaQueNaoExisteErro404() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/pautas/0")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Tentar totalizar votos de uma pauta que nao existe")
    void listarTotalizarVotosPautaQueNaoExisteErro404() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/pautas/totalizar-votos/0")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

}
