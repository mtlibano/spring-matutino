package br.com.trier.springmatutino.repositories;

import br.com.trier.springmatutino.domain.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer> {

}
