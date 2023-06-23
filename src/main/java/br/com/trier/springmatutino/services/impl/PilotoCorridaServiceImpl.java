package br.com.trier.springmatutino.services.impl;

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.PilotoCorrida;
import br.com.trier.springmatutino.repositories.PilotoCorridaRepository;
import br.com.trier.springmatutino.services.PilotoCorridaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PilotoCorridaServiceImpl implements PilotoCorridaService {

    @Autowired
    PilotoCorridaRepository repository;
    
    private void checkPilotoCorrida(PilotoCorrida pilotoCorrida) {
    	if (pilotoCorrida.getColocacao() == null) {
    		throw new ViolacaoIntegridade("Colocacao null!");
    	}
    	if (pilotoCorrida.getColocacao() == 0) {
    		throw new ViolacaoIntegridade("Colocacao zero!");
    	}
    }

    @Override
    public PilotoCorrida salvar(PilotoCorrida pilotoCorrida) {
    	checkPilotoCorrida(pilotoCorrida);
        return repository.save(pilotoCorrida);
    }

    @Override
    public PilotoCorrida findById(Integer id) {
    	return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("ID %s inválido!".formatted(id)));
    }
    
    @Override
    public List<PilotoCorrida> listAll() {
    	List<PilotoCorrida> list = repository.findAll();
		if (list.isEmpty()) {
			throw new ObjetoNaoEncontrado("Nenhum PilotoCorrida cadastrado!");
		}
		return list;
    }

    @Override
    public PilotoCorrida update(PilotoCorrida pilotoCorrida) {
    	checkPilotoCorrida(pilotoCorrida);
    	findById(pilotoCorrida.getId());
        return repository.save(pilotoCorrida);
    }

    @Override
    public void delete(Integer id) {
        PilotoCorrida pilotoCorrida = findById(id);
        repository.delete(pilotoCorrida);
    }

	@Override
	public List<PilotoCorrida> findByColocacao(Integer colocacao) {
		List<PilotoCorrida> list = repository.findByColocacao(colocacao);
		if (list.isEmpty()) {
			throw new ObjetoNaoEncontrado("Nenhum PilotoCorrida nessa colocacao: %s".formatted(colocacao));
		}
		return list;
	}

	@Override
	public List<PilotoCorrida> findByPiloto(Piloto piloto) {
		List<PilotoCorrida> list = repository.findByPiloto(piloto);
		if (list.isEmpty()) {
			throw new ObjetoNaoEncontrado("Nenhum PilotoCorrida com esse piloto: %s".formatted(piloto.getId()));
		}
		return list;
	}

	@Override
	public List<PilotoCorrida> findByCorridaOrderByColocacaoAsc(Corrida corrida) {
		List<PilotoCorrida> list = repository.findByCorridaOrderByColocacaoAsc(corrida);
		if (list.isEmpty()) {
			throw new ObjetoNaoEncontrado("Nenhum PilotoCorrida nessa corrida: %s".formatted(corrida.getId()));
		}
		return list;
	}
	
	@Override
	public List<PilotoCorrida> findByColocacaoBetweenAndCorrida(Integer colocacaoInicial, Integer colocacaoFinal, Corrida corrida) {
		List<PilotoCorrida> list = repository.findByColocacaoBetweenAndCorrida(colocacaoInicial, colocacaoFinal, corrida);
		if (list.isEmpty()) {
			throw new ObjetoNaoEncontrado("Nenhum PilotoCorrida nessa corrida: %s, nesse intervalo de colocacao: %s e %s".formatted(corrida.getId(), colocacaoInicial, colocacaoFinal));
		}
		return list;
	}

	@Override
	public PilotoCorrida findByPilotoAndCorrida(Piloto piloto, Corrida corrida) {
		PilotoCorrida pilotoCorrida = repository.findByPilotoAndCorrida(piloto, corrida);
		if (pilotoCorrida == null) {
			throw new ObjetoNaoEncontrado("Nenhum PilotoCorrida com esse piloto: %s, nessa corrida: %s".formatted(piloto.getId(), corrida.getId()));
		}
		return pilotoCorrida;
	}

}