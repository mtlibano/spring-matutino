package br.com.trier.springmatutino.repositories;

import br.com.trier.springmatutino.domain.Corrida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorridaRepository extends JpaRepository<Corrida, Integer> {

}