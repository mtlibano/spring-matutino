package br.com.trier.springmatutino.services.impl;

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.repositories.CorridaRepository;
import br.com.trier.springmatutino.services.CorridaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorridaServiceImpl implements CorridaService {

    @Autowired
    CorridaRepository repository;

    @Override
    public Corrida salvar(Corrida corrida) {
        return repository.save(corrida);
    }

    @Override
    public List<Corrida> listtAll() {
        return repository.findAll();
    }

    @Override
    public Corrida findById(Integer id) {
        Optional<Corrida> corrida = repository.findById(id);
        return corrida.orElse(null);
    }

    @Override
    public Corrida update(Corrida corrida) {
        return repository.save(corrida);
    }

    @Override
    public void delete(Integer id) {
        Corrida corrida = findById(id);
        if (corrida != null) {
            repository.delete(corrida);
        }
    }

}