package br.com.trier.springmatutino.repositories;

import br.com.trier.springmatutino.domain.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampeonatoRepository extends JpaRepository<Campeonato, Integer> {

    List<Campeonato> findByDescricaoIgnoreCase(String descricao);
    List<Campeonato> findByDescricaoContains(String descricao);
    List<Campeonato> findByAno(Integer ano);
    List<Campeonato> findByAnoBetween(Integer ano1, Integer ano2);

}
