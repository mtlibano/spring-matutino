package br.com.trier.springmatutino.services.impl;

import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.repositories.PistaRepository;
import br.com.trier.springmatutino.services.PistaService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PistaServiceImpl implements PistaService {

    @Autowired
    PistaRepository repository;
    
    private void checkPista(Pista pista) {
    	if (pista.getTamanho() == null || pista.getTamanho() == 0) {
    		throw new ViolacaoIntegridade("Tamanho inválido!");
    	}
    }

    @Override
    public Pista salvar(Pista pista) {
    	checkPista(pista);
        return repository.save(pista);
    }

    @Override
    public Pista findById(Integer id) {
    	return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Pista id %s não existe".formatted(id)));
    }

    @Override
    public List<Pista> listAll() {
    	List<Pista> lista = repository.findAll();
    	if (lista.isEmpty()) {
    		throw new ObjetoNaoEncontrado("Nenhuma pista cadastrada");
    	}
        return lista;
    }

    @Override
    public Pista update(Pista pista) {
    	findById(pista.getId());
    	checkPista(pista);
        return repository.save(pista);
    }

    @Override
    public void delete(Integer id) {
        Pista pista = findById(id);
        repository.delete(pista);
    }

	@Override
	public List<Pista> findByTamanhoBetween(Integer tamInicial, Integer tamFinal) {
		List<Pista> lista = repository.findByTamanhoBetween(tamInicial, tamFinal);
    	if (lista.isEmpty()) {
    		throw new ObjetoNaoEncontrado("Nenhuma pista encontrada nesse intervalo de tamanho entre %s e %s".formatted(tamInicial, tamFinal));
    	}
        return lista;
	}

	@Override
	public List<Pista> findByPaisOrderByTamanhoDesc(Pais pais) {
		List<Pista> lista = repository.findByPaisOrderByTamanhoDesc(pais);
    	if (lista.isEmpty()) {
    		throw new ObjetoNaoEncontrado("Nenhuma pista no pais %s".formatted(pais.getName()));
    	}
        return lista;
	}

}