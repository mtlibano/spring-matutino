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

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class CorridaServiceImpl implements CorridaService {

	@Autowired
	CorridaRepository repository;

	private void checkCorrida(Corrida corrida) {
		if (corrida.getData().isBefore(ZonedDateTime.now())) {
			throw new ViolacaoIntegridade("Data inválida!");
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
	public List<Corrida> listAll() {
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
		repository.delete(corrida);
	}

	@Override
	public List<Corrida> findByData(ZonedDateTime data) {
		List<Corrida> list = repository.findByData(data);
		if (list.isEmpty()) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida na data: %s".formatted(data));
		}
		return list;
	}

	@Override
	public List<Corrida> findByPista(Pista pista) {
		List<Corrida> list = repository.findByPista(pista);
		if (list.isEmpty()) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida na pista: %s".formatted(pista.getId()));
		}
		return list;
	}

	@Override
	public List<Corrida> findByCampeonato(Campeonato campeonato) {
		List<Corrida> list = repository.findByCampeonato(campeonato);
		if (list.isEmpty()) {
			throw new ObjetoNaoEncontrado("Nenhuma corrida no campeonato: %s".formatted(campeonato.getDescricao()));
		}
		return list;
	}

}