package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Pista;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
public class PistaServiceTest extends BaseTests {

    @Autowired
    PistaService pistaService;

    @Test
    @DisplayName("Buscar pista por id")
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void findByIdTest() {
        var pista = pistaService.findById(1);
        assertThat(pista).isNotNull();
        assertEquals(1, pista.getId());
        assertEquals(1000, pista.getTamanho());
    }

    @Test
    @DisplayName("Buscar pista por id inexistente")
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void findByIdTestInexistente() {
        var pista = pistaService.findById(4);
        assertThat(pista).isNull();
    }

    @Test
    @DisplayName("Listar todos")
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void listAll() {
        var lista = pistaService.listAll();
        assertEquals(3, lista.size());
    }

    @Test
    @DisplayName("Cadastrar pista")
    void salvar() {
        Pista pista = pistaService.salvar(new Pista(null, 4000, (new Pais(4, "Peru"))));
        assertThat(pista).isNotNull();
        var pistaTest = pistaService.findById(1);
        assertEquals(1, pistaTest.getId());
        assertEquals(4000, pistaTest.getTamanho());
        assertEquals("Peru", pistaTest.getPais().getName());
    }

    @Test
    @DisplayName("Update pista")
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void updateTest() {
        Pista pista = pistaService.update(new Pista(2, 5000, (new Pais(4, "Peru"))));
        assertThat(pista).isNotNull();
        var pistaTest = pistaService.findById(2);
        assertEquals(2, pistaTest.getId());
        assertEquals(5000, pistaTest.getTamanho());
        assertEquals("Peru", pistaTest.getPais().getName());
    }

    @Test
    @DisplayName("Delete pista")
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void deleteTest() {
        pistaService.delete(2);
        List<Pista> lista = pistaService.listAll();
        assertEquals(2, lista.size());
    }

}