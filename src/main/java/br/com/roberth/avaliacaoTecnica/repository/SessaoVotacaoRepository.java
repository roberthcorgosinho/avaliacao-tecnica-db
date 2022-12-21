package br.com.roberth.avaliacaoTecnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roberth.avaliacaoTecnica.model.entidades.SessaoVotacao;


@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {

}
