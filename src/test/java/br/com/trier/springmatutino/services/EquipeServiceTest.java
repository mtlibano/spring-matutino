package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
public class EquipeServiceTest extends BaseTests {

    @Autowired
    EquipeService equipeService;

    @Test
    @DisplayName("Buscar equipe por id")
    @Sql(scripts =  "classpath:/resources/sqls/equipe.sql")
    void findByIdTest() {
        var equipe = equipeService.findById(1);
        assertThat(equipe).isNotNull();
        assertEquals(1, equipe.getId());
        assertEquals("Mercedez", equipe.getName());
    }

    @Test
    @DisplayName("Buscar equipe por id inexistente")
    @Sql(scripts =  "classpath:/resources/sqls/equipe.sql")
    void findByIdInexistenteTest() {
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> equipeService.findById(10));
        assertEquals("Equipe 10 não encontrada!", exception.getMessage());
    }

    @Test
    @DisplayName("Listar todos")
    @Sql(scripts =  "classpath:/resources/sqls/equipe.sql")
    void listAll() {
        var lista = equipeService.listAll();
        assertEquals(3, lista.size());
    }

    @Test
    @DisplayName("Listar todos vazio")
    @Sql(scripts =  "classpath:/resources/sqls/equipe.sql")
    void listAllVazio() {
        equipeService.delete(1);
        equipeService.delete(2);
        equipeService.delete(3);
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> equipeService.listAll());
        assertEquals("Void!", exception.getMessage());
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
    @DisplayName("Cadastrar equipe existente")
    void salvarExistente() {
        equipeService.salvar(new Equipe(null, "Ferrari"));
        var exception = assertThrows(ViolacaoIntegridade.class, () -> equipeService.salvar(new Equipe(null, "Ferrari")));
        assertEquals("Equipe já cadastrada! Ferrari", exception.getMessage());
    }

    @Test
    @DisplayName("Update equipe")
    @Sql(scripts =  "classpath:/resources/sqls/equipe.sql")
    void updateTest() {
        Equipe equipe = equipeService.update(new Equipe(2, "Ferrari"));
        assertThat(equipe).isNotNull();
        var equipeTest = equipeService.findById(2);
        assertEquals(2, equipeTest.getId());
        assertEquals("Ferrari", equipeTest.getName());
    }

    @Test
    @DisplayName("Update equipe existente")
    @Sql(scripts =  "classpath:/resources/sqls/equipe.sql")
    void updateExistenteTest() {
        var exception = assertThrows(ViolacaoIntegridade.class, () -> equipeService.update(new Equipe(2, "RedBull")));
        assertEquals("Equipe já cadastrada! RedBull", exception.getMessage());
    }

    @Test
    @DisplayName("Update equipe inexistente")
    @Sql(scripts =  "classpath:/resources/sqls/equipe.sql")
    void updateInexistenteTest() {
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> equipeService.update(new Equipe(10, "Ferrari")));
        assertEquals("Equipe 10 não encontrada!", exception.getMessage());
    }

    @Test
    @DisplayName("Delete equipe")
    @Sql(scripts =  "classpath:/resources/sqls/equipe.sql")
    void deleteTest() {
        equipeService.delete(2);
        List<Equipe> lista = equipeService.listAll();
        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Delete equipe inexistente")
    @Sql(scripts =  "classpath:/resources/sqls/equipe.sql")
    void deleteInexistenteTest() {
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> equipeService.delete(10));
        assertEquals("Equipe 10 não encontrada!", exception.getMessage());
        List<Equipe> lista = equipeService.listAll();
        assertEquals(3, lista.size());
    }

    @Test
    @DisplayName("Buscar equipe por nome")
    @Sql(scripts =  "classpath:/resources/sqls/equipe.sql")
    void findByNameTest() {
        List<Equipe> lista = equipeService.findByNameIgnoreCase("mercedez");
        assertEquals(1, lista.size());
        lista = equipeService.findByNameIgnoreCase("mclaren");
        assertEquals(1, lista.size());
    }

    @Test
    @DisplayName("Buscar equipe por nome/contains")
    @Sql(scripts =  "classpath:/resources/sqls/equipe.sql")
    void findByNameContainsTest() {
        List<Equipe> lista = equipeService.findByNameContainsIgnoreCase("mc");
        assertEquals(1, lista.size());
        lista = equipeService.findByNameContainsIgnoreCase("m");
        assertEquals(2, lista.size());
    }

}