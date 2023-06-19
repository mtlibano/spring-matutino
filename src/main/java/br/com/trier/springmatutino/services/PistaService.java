package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.domain.Pista;

import java.util.List;

public interface PistaService {

    Pista salvar(Pista pista);
    List<Pista> listAll();
    Pista findById(Integer id);
    Pista update(Pista pista);
    void delete(Integer id);

}
