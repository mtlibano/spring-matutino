package br.com.trier.springmatutino.repositories;

import br.com.trier.springmatutino.domain.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer> {

    List<Equipe> findByNameIgnoreCase(String name);
    List<Equipe> findByNameContainsIgnoreCase(String name);
    Equipe findByName(String name);

}