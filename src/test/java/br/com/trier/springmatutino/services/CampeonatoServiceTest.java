package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Campeonato;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
public class CampeonatoServiceTest extends BaseTests {

    @Autowired
    CampeonatoService campeonatoService;

    @Test
    @DisplayName("Buscar campeonato por id")
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
    void findByIdTest() {
        var campeonato = campeonatoService.findById(1);
        assertThat(campeonato).isNotNull();
        assertEquals(1, campeonato.getId());
        assertEquals("Formula1", campeonato.getDescricao());
        assertEquals(2020, campeonato.getAno());
    }

    @Test
    @DisplayName("Buscar campeonato por id inexistente")
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
    void findByIdTestInexistente() {
        var campeonato = campeonatoService.findById(4);
        assertThat(campeonato).isNull();
    }

    @Test
    @DisplayName("Listar todos")
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
    void listAll() {
        var lista = campeonatoService.listAll();
        assertEquals(3, lista.size());
    }

    @Test
    @DisplayName("Cadastrar campeonato")
    void salvar() {
        Campeonato campeonato = campeonatoService.salvar(new Campeonato(null, "LeMans", 2023));
        assertThat(campeonato).isNotNull();
        var campeonatoTest = campeonatoService.findById(1);
        assertEquals(1, campeonatoTest.getId());
        assertEquals("LeMans", campeonatoTest.getDescricao());
        assertEquals(2023, campeonatoTest.getAno());
    }

    @Test
    @DisplayName("Update campeonato")
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
    void updateTest() {
        Campeonato campeonato = campeonatoService.update(new Campeonato(2, "Nascar", 2021));
        assertThat(campeonato).isNotNull();
        var campeonatoTest = campeonatoService.findById(2);
        assertEquals(2, campeonatoTest.getId());
        assertEquals("Nascar", campeonatoTest.getDescricao());
        assertEquals(2021, campeonatoTest.getAno());
    }

    @Test
    @DisplayName("Delete campeonato")
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
    void deleteTest() {
        campeonatoService.delete(2);
        List<Campeonato> lista = campeonatoService.listAll();
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Buscar campeonato por nome")
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
    void findByNameTest() {
        List<Campeonato> lista = campeonatoService.findByDescricaoIgnoreCase("formula1");
        assertEquals(1, lista.size());
        lista = campeonatoService.findByDescricaoIgnoreCase("formuladrift");
        assertEquals(1, lista.size());
    }

    @Test
    @DisplayName("Buscar campeonato por nome/contains")
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
    void findByNameContainsTest() {
        List<Campeonato> lista = campeonatoService.findByDescricaoContains("Formula");
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Buscar campeonato por ano")
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
    void findByAnoTest() {
        List<Campeonato> lista = campeonatoService.findByAno(2020);
        assertEquals(1, lista.size());
        assertEquals("Formula1", lista.get(0).getDescricao());
    }

    @Test
    @DisplayName("Buscar campeonato por intervalo de ano")
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
    void findByAnoBetweenTest() {
        List<Campeonato> lista = campeonatoService.findByAnoBetween(2020, 2021);
        assertEquals(2, lista.size());
        assertEquals("Formula1", lista.get(0).getDescricao());
        assertEquals("Nascar", lista.get(1).getDescricao());
    }

}