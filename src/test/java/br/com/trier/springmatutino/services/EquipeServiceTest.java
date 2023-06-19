package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Equipe;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
public class EquipeServiceTest extends BaseTests {

    @Autowired
    EquipeService equipeService;

    @Test
    @DisplayName("Buscar equipe por id")
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    void findByIdTest() {
        var equipe = equipeService.findById(1);
        assertThat(equipe).isNotNull();
        assertEquals(1, equipe.getId());
        assertEquals("Mercedez", equipe.getName());
    }

    @Test
    @DisplayName("Buscar equipe por id inexistente")
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    void findByIdTestInexistente() {
        var equipe = equipeService.findById(4);
        assertThat(equipe).isNull();
    }

    @Test
    @DisplayName("Listar todos")
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    void listAll() {
        var lista = equipeService.listAll();
        assertEquals(3, lista.size());
    }

    @Test
    @DisplayName("Cadastrar equipe")
    void salvar() {
        Equipe equipe = equipeService.salvar(new Equipe(null, "Ferrari"));
        assertThat(equipe).isNotNull();
        var equipeTest = equipeService.findById(1);
        assertEquals(1, equipeTest.getId());
        assertEquals("Ferrari", equipeTest.getName());
    }

    @Test
    @DisplayName("Update equipe")
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    void updateTest() {
        Equipe equipe = equipeService.update(new Equipe(2, "Ferrari"));
        assertThat(equipe).isNotNull();
        var equipeTest = equipeService.findById(2);
        assertEquals(2, equipeTest.getId());
        assertEquals("Ferrari", equipeTest.getName());
    }

    @Test
    @DisplayName("Delete equipe")
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    void deleteTest() {
        equipeService.delete(2);
        List<Equipe> lista = equipeService.listAll();
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Buscar equipe por nome")
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    void findByNameTest() {
        List<Equipe> lista = equipeService.findByNameIgnoreCase("mercedez");
        assertEquals(1, lista.size());
        lista = equipeService.findByNameIgnoreCase("mclaren");
        assertEquals(1, lista.size());
    }

    @Test
    @DisplayName("Buscar equipe por nome/contains")
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    void findByNameContainsTest() {
        List<Equipe> lista = equipeService.findByNameContains("Mc");
        assertEquals(1, lista.size());
        lista = equipeService.findByNameContains("M");
        assertEquals(2, lista.size());
    }

}