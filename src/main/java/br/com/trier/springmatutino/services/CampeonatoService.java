package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.domain.Campeonato;
import java.util.List;

public interface CampeonatoService {

    Campeonato salvar(Campeonato campeonato);
    List<Campeonato> listAll();
    Campeonato findById(Integer id);
    Campeonato update(Campeonato equipe);
    void delete(Integer id);

}
