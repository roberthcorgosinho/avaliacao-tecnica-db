/**
 * 
 */
package br.com.roberth.avaliacaoTecnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.roberth.avaliacaoTecnica.model.entidades.Pauta;

/**
 * @author Roberth.Corgosinho
 *
 */
@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

}
