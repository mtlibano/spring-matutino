package br.com.trier.springmatutino.repositories;

import br.com.trier.springmatutino.domain.Piloto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PilotoRepository extends JpaRepository<Piloto, Integer> {
}
