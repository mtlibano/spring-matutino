package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.PilotoCorrida;
import java.util.List;

public interface PilotoCorridaService {

    PilotoCorrida salvar(PilotoCorrida pilotoCorrida);
    PilotoCorrida findById(Integer id);
    List<PilotoCorrida> listAll();
    PilotoCorrida update(PilotoCorrida pilotoCorrida);
    void delete(Integer id);
    List<PilotoCorrida> findByColocacao(Integer colocacao);
	List<PilotoCorrida> findByPiloto(Piloto piloto);
	List<PilotoCorrida> findByCorridaOrderByColocacaoAsc(Corrida corrida);
	List<PilotoCorrida> findByColocacaoBetweenAndCorrida(Integer colocacaoInicial, Integer colocacaoFinal, Corrida corrida);
	PilotoCorrida findByPilotoAndCorrida(Piloto piloto, Corrida corrida);
	
}