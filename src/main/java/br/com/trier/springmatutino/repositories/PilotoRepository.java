package br.com.trier.springmatutino.repositories;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PilotoRepository extends JpaRepository<Piloto, Integer> {
	
	List<Piloto> findByName(String name);
	List<Piloto> findByPilotoPais(Pais pais);
	List<Piloto> findByPilotoEquipe(Equipe equipe);

}