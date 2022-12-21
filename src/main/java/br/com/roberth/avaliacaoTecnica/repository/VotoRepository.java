package br.com.roberth.avaliacaoTecnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roberth.avaliacaoTecnica.model.entidades.Voto;
import br.com.roberth.avaliacaoTecnica.model.entidades.VotoId;

@Repository
public interface VotoRepository extends JpaRepository<Voto, VotoId> {

}
