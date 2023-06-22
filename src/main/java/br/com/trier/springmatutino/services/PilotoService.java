package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;

import java.util.List;

public interface PilotoService {

    Piloto salvar(Piloto piloto);
    Piloto findById(Integer id);
    List<Piloto> listAll();
    Piloto update(Piloto piloto);
    void delete(Integer id);
    List<Piloto> findByNameIgnoreCase(String name);
	List<Piloto> findByPaisOrderByNameDesc(Pais pais);
	List<Piloto> findByEquipeOrderByNameDesc(Equipe equipe);

}