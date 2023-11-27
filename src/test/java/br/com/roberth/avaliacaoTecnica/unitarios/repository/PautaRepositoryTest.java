package br.com.roberth.avaliacaoTecnica.unitarios.repository;

import br.com.roberth.avaliacaoTecnica.model.entidades.Pauta;
import br.com.roberth.avaliacaoTecnica.repository.PautaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PautaRepositoryTest {

    @Autowired
    private PautaRepository pautaRepository;

    @Test
    @DisplayName("Incluir pauta valida")
    void incluirUmaPauta() {
        Pauta pauta = new Pauta();
        pauta.setAssunto("Teste automatizado");
        Pauta novaPauta = pautaRepository.save(pauta);
        assertThat(novaPauta).isNotNull();
        assertThat(novaPauta.getAssunto()).isEqualTo(pauta.getAssunto());
        assertThat(novaPauta.getId()).isNotNull();
    }

    @Test
    @DisplayName("Incluir pauta sem assunto")
    void incluirUmaPautaSemAssunto() {
        Pauta novaPauta = null;
        try {
            Pauta pauta = new Pauta();
            novaPauta = pautaRepository.save(pauta);
        } catch (Exception e) {
            novaPauta = null;
        } finally {
            assertThat(novaPauta).isNull();
        }
    }

}
