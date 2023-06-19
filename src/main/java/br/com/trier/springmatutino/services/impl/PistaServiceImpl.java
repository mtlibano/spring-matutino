package br.com.trier.springmatutino.services.impl;

import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.repositories.PistaRepository;
import br.com.trier.springmatutino.services.PistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PistaServiceImpl implements PistaService {

    @Autowired
    PistaRepository repository;

    @Override
    public Pista salvar(Pista pista) {
        return repository.save(pista);
    }

    @Override
    public List<Pista> listAll() {
        return repository.findAll();
    }

    @Override
    public Pista findById(Integer id) {
        Optional<Pista> opt = repository.findById(id);
        return opt.orElse(null);
    }

    @Override
    public Pista update(Pista pista) {
        return repository.save(pista);
    }

    @Override
    public void delete(Integer id) {
        Pista pista = findById(id);
        if (pista != null) {
            repository.delete(pista);
        }
    }

}