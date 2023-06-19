package br.com.trier.springmatutino.services.impl;

import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.repositories.PilotoRepository;
import br.com.trier.springmatutino.services.PilotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PilotoServiceImpl implements PilotoService {

    @Autowired
    PilotoRepository repository;

    @Override
    public Piloto salvar(Piloto piloto) {
        return repository.save(piloto);
    }

    @Override
    public List<Piloto> listtAll() {
        return repository.findAll();
    }

    @Override
    public Piloto findById(Integer id) {
        Optional<Piloto> opt = repository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public Piloto update(Piloto piloto) {
        return repository.save(piloto);
    }

    @Override
    public void delete(Integer id) {
        Piloto piloto = findById(id);
        if (piloto != null) {
            repository.delete(piloto);
        }
    }

}