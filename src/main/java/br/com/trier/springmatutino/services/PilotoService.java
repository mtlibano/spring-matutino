package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;

import java.util.List;

public interface PilotoService {

    Piloto salvar(Piloto piloto);
    Piloto findById(Integer id);
    List<Piloto> listtAll();
    Piloto update(Piloto piloto);
    void delete(Integer id);
    List<Piloto> findByName(String name);
    List<Piloto> findByPilotoPais(Pais pais);
	List<Piloto> findByPilotoEquipe(Equipe equipe);

}