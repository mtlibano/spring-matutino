package br.com.trier.springmatutino.services.impl;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.repositories.EquipeRepository;
import br.com.trier.springmatutino.services.EquipeService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipeServiceImpl implements EquipeService {

    @Autowired
    EquipeRepository repository;

    private void verificarNome(Equipe obj) {
        Equipe equipe = repository.findByName(obj.getName());
        if (equipe != null && equipe.getId() != obj.getId()) {
            throw new ViolacaoIntegridade("Equipe já cadastrada! %s".formatted(obj.getName()));
        }
    }

    @Override
    public Equipe salvar(Equipe equipe) {
        verificarNome(equipe);
        return repository.save(equipe);
    }

    @Override
    public List<Equipe> listAll() {
        List<Equipe> lista = repository.findAll();
        if (lista.isEmpty()) {
            throw new ObjetoNaoEncontrado("Void!");
        }
        return lista;
    }

    @Override
    public Equipe findById(Integer id) {
        Optional<Equipe> opt = repository.findById(id);
        return opt.orElseThrow(() -> new ObjetoNaoEncontrado("Equipe %s não encontrada!".formatted(id)));
    }

    @Override
    public Equipe update(Equipe equipe) {
        verificarNome(equipe);
        findById(equipe.getId());
        return repository.save(equipe);
    }

    @Override
    public void delete(Integer id) {
        Equipe equipe = findById(id);
        repository.delete(equipe);
    }

    @Override
    public List<Equipe> findByNameIgnoreCase(String name) {
        return repository.findByNameIgnoreCase(name);
    }

    @Override
    public List<Equipe> findByNameContainsIgnoreCase(String name) {
        return repository.findByNameContainsIgnoreCase(name);
    }

}