package br.com.trier.springmatutino.services.impl;

import br.com.trier.springmatutino.domain.PilotoCorrida;
import br.com.trier.springmatutino.repositories.PilotoCorridaRepository;
import br.com.trier.springmatutino.services.PilotoCorridaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PilotoCorridaServiceImpl implements PilotoCorridaService {

    @Autowired
    PilotoCorridaRepository repository;

    @Override
    public PilotoCorrida salvar(PilotoCorrida pilotoCorrida) {
        return repository.save(pilotoCorrida);
    }

    @Override
    public List<PilotoCorrida> listtAll() {
        return repository.findAll();
    }

    @Override
    public PilotoCorrida findById(Integer id) {
        Optional<PilotoCorrida> opt = repository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public PilotoCorrida update(PilotoCorrida pilotoCorrida) {
        return repository.save(pilotoCorrida);
    }

    @Override
    public void delete(Integer id) {
        PilotoCorrida pilotoCorrida = findById(id);
        if (pilotoCorrida != null) {
            repository.delete(pilotoCorrida);
        }
    }

}