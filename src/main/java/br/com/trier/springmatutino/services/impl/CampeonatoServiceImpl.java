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

}
