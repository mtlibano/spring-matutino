package br.com.trier.springmatutino.services.impl;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.repositories.CorridaRepository;
import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class CorridaServiceImpl implements CorridaService {

    @Autowired
    CorridaRepository repository;
    
    private void checkCorrida(Corrida corrida) {
    	if (corrida == null) {
    		throw new ViolacaoIntegridade("Corrida null!");
    	}
    	LocalDate data = corrida.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    	if (data.isBefore(LocalDate.now())) {
    		throw new ViolacaoIntegridade("Data inválida");
    	}
    }

    @Override
    public Corrida salvar(Corrida corrida) {
    	checkCorrida(corrida);
        return repository.save(corrida);
    }

    @Override
    public Corrida findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("ID %s inválido!".formatted(id)));
    }

    @Override
    public List<Corrida> listtAll() {
    	List<Corrida> list = repository.findAll();
    	if (list.isEmpty()) {
    		throw new ObjetoNaoEncontrado("Nenhuma corrida cadastrada!");
    	}
        return list;
    }

    @Override
    public Corrida update(Corrida corrida) {
    	findById(corrida.getId());
    	checkCorrida(corrida);
        return repository.save(corrida);
    }

    @Override
    public void delete(Integer id) {
        Corrida corrida = findById(id);
        if (corrida != null) {
            repository.delete(corrida);
        }
    }

	@Override
	public List<Corrida> findByCorridaPista(Pista pista) {
		List<Corrida> list = repository.findByCorridaPista(pista);
		if (list.isEmpty()) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida nessa pista: %s".formatted(pista.getId()));
		}
		return list;
	}

	@Override
	public List<Corrida> findByCorridaCampeonato(Campeonato campeonato) {
		List<Corrida> list = repository.findByCorridaCampeonato(campeonato);
		if (list.isEmpty()) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida nesse campeonato: %s".formatted(campeonato.getId()));
		}
		return list;
	}

}