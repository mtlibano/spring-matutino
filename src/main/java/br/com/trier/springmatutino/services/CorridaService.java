package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.domain.Corrida;
import java.util.List;

public interface CorridaService {

    Corrida salvar(Corrida corrida);
    List<Corrida> listtAll();
    Corrida findById(Integer id);
    Corrida update(Corrida corrida);
    void delete(Integer id);
    
}