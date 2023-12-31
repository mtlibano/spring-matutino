package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.domain.Campeonato;

import java.util.List;

public interface CampeonatoService {

    Campeonato salvar(Campeonato campeonato);
    List<Campeonato> listAll();
    Campeonato findById(Integer id);
    Campeonato update(Campeonato campeonato);
    void delete(Integer id);
    List<Campeonato> findByDescricaoIgnoreCase(String descricao);
    List<Campeonato> findByDescricaoContainsIgnoreCase(String descricao);
    List<Campeonato> findByAno(Integer ano);
    List<Campeonato> findByAnoBetween(Integer ano1, Integer ano2);
    List<Campeonato> findByDescricaoIgnoreCaseAndAnoEquals(String descricao, Integer ano);

}