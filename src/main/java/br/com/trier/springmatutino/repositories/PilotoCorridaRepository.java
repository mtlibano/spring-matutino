package br.com.trier.springmatutino.repositories;

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.PilotoCorrida;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PilotoCorridaRepository extends JpaRepository<PilotoCorrida, Integer> {
	
	List<PilotoCorrida> findByColocacao(Integer colocacao);
	List<PilotoCorrida> findByPiloto(Piloto piloto);
	List<PilotoCorrida> findByCorridaOrderByColocacaoAsc(Corrida corrida);
	List<PilotoCorrida> findByColocacaoBetweenAndCorrida(Integer colocacaoInicial, Integer colocacaoFinal, Corrida corrida);
	PilotoCorrida findByPilotoAndCorrida(Piloto piloto, Corrida corrida);
	
}