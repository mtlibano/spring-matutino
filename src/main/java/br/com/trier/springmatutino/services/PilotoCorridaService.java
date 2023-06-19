package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.domain.PilotoCorrida;
import java.util.List;

public interface PilotoCorridaService {

    PilotoCorrida salvar(PilotoCorrida pilotoCorrida);
    List<PilotoCorrida> listtAll();
    PilotoCorrida findById(Integer id);
    PilotoCorrida update(PilotoCorrida pilotoCorrida);
    void delete(Integer id);

}