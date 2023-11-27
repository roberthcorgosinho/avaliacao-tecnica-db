package br.com.roberth.avaliacaoTecnica.unitarios.repository;

import br.com.roberth.avaliacaoTecnica.model.entidades.Pauta;
import br.com.roberth.avaliacaoTecnica.model.entidades.SessaoVotacao;
import br.com.roberth.avaliacaoTecnica.repository.SessaoVotacaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SessaoVotacaoRepositoryTest {

    @Autowired
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Pauta pauta;

    @Transactional
    void cadastrosIniciaisNecessarios() {
        this.pauta = new Pauta();
        this.pauta.setAssunto("Teste Automatizado");
        testEntityManager.persist(this.pauta);
    }

    @Test
    @DisplayName("Incluir sessao de votacao valida")
    void incluirUmaSessao() {
        cadastrosIniciaisNecessarios();
        SessaoVotacao sessaoVotacao = new SessaoVotacao();
        sessaoVotacao.setPauta(this.pauta);
        sessaoVotacao.setDuracaoEmMinutos(5);
        SessaoVotacao novaSessao = sessaoVotacaoRepository.save(sessaoVotacao);
        assertThat(novaSessao).isNotNull();
        assertThat(novaSessao.getPauta()).isEqualTo(sessaoVotacao.getPauta());
        assertThat(novaSessao.getDuracaoEmMinutos()).isEqualTo(5);
    }

    @Test
    @DisplayName("Incluir sessao de votacao sem especificar a duracao")
    void incluirUmaSessaoSemDuracao() {
        cadastrosIniciaisNecessarios();
        SessaoVotacao sessaoVotacao = new SessaoVotacao();
        sessaoVotacao.setPauta(this.pauta);
        SessaoVotacao novaSessao = sessaoVotacaoRepository.save(sessaoVotacao);
        assertThat(novaSessao).isNotNull();
        assertThat(novaSessao.getPauta()).isEqualTo(sessaoVotacao.getPauta());
        assertThat(novaSessao.getDuracaoEmMinutos()).isEqualTo(1);
    }

    @Test
    @DisplayName("Tentar incluir sessao de votacao sem especificar a pauta")
    void incluirUmaSessaoSemPauta() {
        SessaoVotacao novaSessao = null;
        try {
            cadastrosIniciaisNecessarios();
            SessaoVotacao sessaoVotacao = new SessaoVotacao();
            sessaoVotacao.setDuracaoEmMinutos(1);
            novaSessao = sessaoVotacaoRepository.save(sessaoVotacao);
        } catch (Exception e) {
            novaSessao = null;
        } finally {
            assertThat(novaSessao).isNull();
        }
    }

}
