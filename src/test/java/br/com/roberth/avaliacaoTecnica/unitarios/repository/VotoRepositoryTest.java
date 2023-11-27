package br.com.roberth.avaliacaoTecnica.unitarios.repository;

import br.com.roberth.avaliacaoTecnica.enums.EnumRespostaVotacao;
import br.com.roberth.avaliacaoTecnica.model.entidades.Pauta;
import br.com.roberth.avaliacaoTecnica.model.entidades.SessaoVotacao;
import br.com.roberth.avaliacaoTecnica.model.entidades.Voto;
import br.com.roberth.avaliacaoTecnica.model.entidades.VotoId;
import br.com.roberth.avaliacaoTecnica.repository.VotoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VotoRepositoryTest {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Pauta pauta;
    private SessaoVotacao sessaoVotacao;
    private String cpfFake = "61555528279";

    @Transactional
    void cadastrosIniciaisNecessarios() {
        this.pauta = new Pauta();
        this.pauta.setAssunto("Teste Automatizado");
        this.sessaoVotacao = new SessaoVotacao();
        this.sessaoVotacao.setPauta(this.pauta);
        this.pauta.setSessaoVotacao(this.sessaoVotacao);
        testEntityManager.persist(this.pauta);
        testEntityManager.persist(this.sessaoVotacao);
    }

    @Test
    @DisplayName("Incluir um voto valido")
    void incluirUmVoto() {
        cadastrosIniciaisNecessarios();
        Voto voto = new Voto();
        voto.setId(new VotoId());
        voto.getId().setCpf(this.cpfFake);
        voto.getId().setIdSessaoVotacao(this.sessaoVotacao.getId());
        voto.setSessaoVotacao(this.sessaoVotacao);
        voto.setOpcao(EnumRespostaVotacao.NAO);
        Voto novoVoto = votoRepository.save(voto);
        assertThat(novoVoto).isNotNull();
        assertThat(novoVoto.getId()).isEqualTo(voto.getId());
    }

    @Test
    @DisplayName("Tentar incluir um voto com opcao invalida")
    void tentarIncluirUmVotoOpcaoInvalida() {
        Voto novoVoto = null;
        try {
            cadastrosIniciaisNecessarios();
            Voto voto = new Voto();
            voto.setId(new VotoId());
            voto.getId().setCpf(this.cpfFake);
            voto.getId().setIdSessaoVotacao(this.sessaoVotacao.getId());
            voto.setOpcao(EnumRespostaVotacao.valueOf("123"));
            voto.setSessaoVotacao(this.sessaoVotacao);
            novoVoto = votoRepository.save(voto);
        } catch (Exception e) {
            novoVoto = null;
        } finally {
            assertThat(novoVoto).isNull();
        }
    }

    @Test
    @DisplayName("Recuperar um voto especifico")
    void recuperarUmVoto() {
        Optional<Voto> novoVoto = null;
        cadastrosIniciaisNecessarios();
        Voto voto = new Voto();
        voto.setId(new VotoId());
        voto.getId().setCpf(this.cpfFake);
        voto.getId().setIdSessaoVotacao(this.sessaoVotacao.getId());
        voto.setSessaoVotacao(this.sessaoVotacao);
        voto.setOpcao(EnumRespostaVotacao.NAO);
        voto = votoRepository.save(voto);
        novoVoto = votoRepository.findById(voto.getId());
        assertThat(novoVoto.isPresent()).isEqualTo(true);
        assertThat(novoVoto.get()).isEqualTo(voto);
    }

    @Test
    @DisplayName("Listar todos os votos cadastrados")
    void listarVoto() {
        cadastrosIniciaisNecessarios();
        Voto voto = new Voto();
        voto.setId(new VotoId());
        voto.getId().setCpf(this.cpfFake);
        voto.getId().setIdSessaoVotacao(this.sessaoVotacao.getId());
        voto.setSessaoVotacao(this.sessaoVotacao);
        voto.setOpcao(EnumRespostaVotacao.NAO);
        votoRepository.save(voto);
        Collection<Voto> votosCadastrados = votoRepository.findAll();
        assertThat(votosCadastrados.isEmpty()).isEqualTo(false);
    }

}
