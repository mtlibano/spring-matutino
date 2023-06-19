package br.com.trier.springmatutino.services.impl;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.repositories.CampeonatoRepository;
import br.com.trier.springmatutino.services.CampeonatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CampeonatoServiceImpl implements CampeonatoService {

    @Autowired
    CampeonatoRepository repository;

    @Override
    public Campeonato salvar(Campeonato campeonato) {
        return repository.save(campeonato);
    }

    @Override
    public List<Campeonato> listAll() {
        return repository.findAll();
    }

    @Override
    public Campeonato findById(Integer id) {
        Optional<Campeonato> opt = repository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public Campeonato update(Campeonato equipe) {
        return repository.save(equipe);
    }

    @Override
    public void delete(Integer id) {
        Campeonato camp = findById(id);
        if (camp != null) {
            repository.delete(camp);
        }
    }

    @Override
    public List<Campeonato> findByDescricaoIgnoreCase(String descricao) {
        return repository.findByDescricaoIgnoreCase(descricao);
    }

    @Override
    public List<Campeonato> findByDescricaoContainsIgnoreCase(String descricao) {
        return repository.findByDescricaoContainsIgnoreCase(descricao);
    }

    @Override
    public List<Campeonato> findByAno(Integer ano) {
        return repository.findByAno(ano);
    }

    @Override
    public List<Campeonato> findByAnoBetween(Integer ano1, Integer ano2) {
        return repository.findByAnoBetween(ano1, ano2);
    }

	@Override
	public List<Campeonato> findByDescricaoIgnoreCaseAndAnoEquals(String descricao, Integer ano) {
		return repository.findByDescricaoIgnoreCaseAndAnoEquals(descricao, ano);
	}

}