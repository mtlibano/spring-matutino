package br.com.trier.springmatutino.repositories;

import br.com.trier.springmatutino.domain.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampeonatoRepository extends JpaRepository<Campeonato, Integer> {

}
