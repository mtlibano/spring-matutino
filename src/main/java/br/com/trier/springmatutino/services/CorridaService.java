package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pista;

import java.util.List;

public interface CorridaService {

    Corrida salvar(Corrida corrida);
    Corrida findById(Integer id);
    List<Corrida> listtAll();
    Corrida update(Corrida corrida);
    void delete(Integer id);
	List<Corrida> findByCorridaPista(Pista pista);
	List<Corrida> findByCorridaCampeonato(Campeonato campeonato);
    
}