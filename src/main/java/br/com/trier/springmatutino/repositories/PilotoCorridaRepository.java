package br.com.trier.springmatutino.repositories;

import br.com.trier.springmatutino.domain.PilotoCorrida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PilotoCorridaRepository extends JpaRepository<PilotoCorrida, Integer> {

}