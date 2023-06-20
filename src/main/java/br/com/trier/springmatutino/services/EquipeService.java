package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.domain.Equipe;
import java.util.List;

public interface EquipeService {

    Equipe salvar(Equipe equipe);
    List<Equipe> listAll();
    Equipe findById(Integer id);
    Equipe update(Equipe equipe);
    void delete(Integer id);
    List<Equipe> findByNameIgnoreCase(String name);
    List<Equipe> findByNameContainsIgnoreCase(String name);

}