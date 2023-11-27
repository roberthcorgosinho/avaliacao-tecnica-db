package br.com.roberth.avaliacaoTecnica.unitarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SessaoVotacaoControllerTest {

    @Autowired
    private MockMvc mvc;
}
