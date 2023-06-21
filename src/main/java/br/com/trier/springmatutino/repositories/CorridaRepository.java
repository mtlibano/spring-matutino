package br.com.trier.springmatutino.repositories;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pista;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorridaRepository extends JpaRepository<Corrida, Integer> {
	
	List<Corrida> findByCorridaPista(Pista pista);
	List<Corrida> findByCorridaCampeonato(Campeonato campeonato);

}