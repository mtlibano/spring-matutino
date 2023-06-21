package br.com.trier.springmatutino.repositories;

import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Pista;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PistaRepository extends JpaRepository<Pista, Integer> {
	
	List<Pista> findByTamanhoBetween(Integer tamInicial, Integer tamFinal);
	List<Pista> findByPaisOrderByTamanhoDesc(Pais pais);

}