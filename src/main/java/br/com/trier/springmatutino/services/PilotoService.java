package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.domain.Piloto;
import java.util.List;

public interface PilotoService {

    Piloto salvar(Piloto piloto);
    List<Piloto> listtAll();
    Piloto findById(Integer id);
    Piloto update(Piloto piloto);
    void delete(Integer id);

}
