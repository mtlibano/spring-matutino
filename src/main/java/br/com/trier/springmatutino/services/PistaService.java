package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Pista;

import java.util.List;

public interface PistaService {

    Pista salvar(Pista pista);
    Pista findById(Integer id);
    List<Pista> listAll();
    Pista update(Pista pista);
    void delete(Integer id);
    List<Pista> findByTamanhoBetween(Integer tamInicial, Integer tamFinal);
	List<Pista> findByPaisOrderByTamanhoDesc(Pais pais);

}