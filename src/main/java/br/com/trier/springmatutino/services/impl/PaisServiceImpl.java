package br.com.trier.springmatutino.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.repositories.PaisRepository;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;

@Service
public class PaisServiceImpl implements PaisService{
	
	@Autowired
	PaisRepository repository;
	
	private void verificarNome(Pais obj) {
		Pais pais = repository.findByName(obj.getName());
		if (pais != null && pais.getId() != obj.getId()) {
			throw new ViolacaoIntegridade("País já cadastrado: %s".formatted(obj.getName()));
		}
	}

	@Override
	public Pais salvar(Pais pais) {
		verificarNome(pais);
		return repository.save(pais);
	}

	@Override
	public List<Pais> listAll() {
		return repository.findAll();
	}

	@Override
	public Pais findById(Integer id) {
		Optional<Pais> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("País %s não encontrado".formatted(id)));
	}

	@Override
	public Pais update(Pais pais) {
		verificarNome(pais);
		findById(pais.getId());
		return repository.save(pais);
	}

	@Override
	public void delete(Integer id) {
		Pais pais = findById(id);
		repository.delete(pais);
	}

	@Override
	public List<Pais> findByNameIgnoreCase(String name) {
		return repository.findByNameIgnoreCase(name);
	}

	@Override
	public List<Pais> findByNameContainsIgnoreCase(String name) {
		return repository.findByNameContainsIgnoreCase(name);
	}

}