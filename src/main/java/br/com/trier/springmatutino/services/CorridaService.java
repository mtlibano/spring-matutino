package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pista;

import java.time.ZonedDateTime;
import java.util.List;

public interface CorridaService {

    Corrida salvar(Corrida corrida);
    Corrida findById(Integer id);
    List<Corrida> listAll();
    Corrida update(Corrida corrida);
    void delete(Integer id);
	List<Corrida> findByData(ZonedDateTime data);
	List<Corrida> findByPista(Pista pista);
	List<Corrida> findByCampeonato(Campeonato campeonato);
    
}