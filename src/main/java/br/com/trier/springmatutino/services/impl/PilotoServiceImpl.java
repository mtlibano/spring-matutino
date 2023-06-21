package br.com.trier.springmatutino.services.impl;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.repositories.PilotoRepository;
import br.com.trier.springmatutino.services.PilotoService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PilotoServiceImpl implements PilotoService {

    @Autowired
    PilotoRepository repository;
    
    public void checkPiloto(Piloto piloto) {
    	if (piloto == null) {
    		throw new ViolacaoIntegridade("Piloto null!");
    	}
    }

    @Override
    public Piloto salvar(Piloto piloto) {
    	checkPiloto(piloto);
        return repository.save(piloto);
    }

    @Override
    public Piloto findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("ID %s inválido!".formatted(id)));
    }
    
    @Override
    public List<Piloto> listtAll() {
        List<Piloto> list = repository.findAll();
        if (list.isEmpty()) {
        	throw new ObjetoNaoEncontrado("Nenhum piloto cadastrado!");
        }
    	return list;
    }

    @Override
    public Piloto update(Piloto piloto) {
    	findById(piloto.getId());
    	checkPiloto(piloto);
        return repository.save(piloto);
    }

    @Override
    public void delete(Integer id) {
        Piloto piloto = findById(id);
        if (piloto != null) {
            repository.delete(piloto);
        }
    }

	@Override
	public List<Piloto> findByName(String name) {
		List<Piloto> list = repository.findByName(name);
		if (list.isEmpty()) {
			throw new ObjetoNaoEncontrado("Nenhum piloto com o nome: %s".formatted(name));
		}
		return list;
	}

	@Override
	public List<Piloto> findByPilotoPais(Pais pais) {
		List<Piloto> list = repository.findByPilotoPais(pais);
		if (list.isEmpty()) {
			throw new ObjetoNaoEncontrado("Nenhum piloto do país: %s".formatted(pais.getName()));
		}
		return list;
	}

	@Override
	public List<Piloto> findByPilotoEquipe(Equipe equipe) {
		List<Piloto> list = repository.findByPilotoEquipe(equipe);
		if (list.isEmpty()) {
			throw new ObjetoNaoEncontrado("Nenhum piloto da equipe: %s".formatted(equipe.getName()));
		}
		return list;
	}

}