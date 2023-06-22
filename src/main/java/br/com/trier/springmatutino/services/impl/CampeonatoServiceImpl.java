package br.com.trier.springmatutino.services.impl;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.repositories.CampeonatoRepository;
import br.com.trier.springmatutino.services.CampeonatoService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CampeonatoServiceImpl implements CampeonatoService {

    @Autowired
    CampeonatoRepository repository;
    
    private void checkCampeonato(Campeonato camp) {
    	if (camp.getDescricao() == null || camp.getDescricao().equals("")) {
    		throw new ViolacaoIntegridade("Descricao null!");
    	}
    	if (camp.getAno() == null) {
    		throw new ViolacaoIntegridade("Ano null!");
    	}
        if (camp.getAno() < 1990 || camp.getAno() > (LocalDate.now().getYear() + 1)) {
            throw new ViolacaoIntegridade("Ano inválido!");
        }
    }

    @Override
    public Campeonato salvar(Campeonato campeonato) {
        checkCampeonato(campeonato);
        return repository.save(campeonato);
    }

    @Override
    public List<Campeonato> listAll() {
    	List<Campeonato> lista = repository.findAll();
        if (lista.isEmpty()) {
            throw new ObjetoNaoEncontrado("Void!");
        }
        return lista;
    }

    @Override
    public Campeonato findById(Integer id) {
        Optional<Campeonato> opt = repository.findById(id);
        return opt.orElseThrow(() -> new ObjetoNaoEncontrado("Campeonato %s não encontrado!".formatted(id)));
    }

    @Override
    public Campeonato update(Campeonato campeonato) {
        checkCampeonato(campeonato);
        findById(campeonato.getId());
        return repository.save(campeonato);
    }

    @Override
    public void delete(Integer id) {
        Campeonato camp = findById(id);
        repository.delete(camp);
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