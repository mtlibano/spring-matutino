package br.com.trier.springmatutino.repositories;

import br.com.trier.springmatutino.domain.Pista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PistaRepository extends JpaRepository<Pista, Integer> {

}
