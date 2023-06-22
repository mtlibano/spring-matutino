package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Pista;
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
public class PistaServiceTest extends BaseTests {

    @Autowired
    PistaService pistaService;
    
    @Autowired
    PaisService paisService;

    @Test
    @DisplayName("Buscar por ID")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void testFindById() {
        Pista pista = pistaService.findById(1);
        assertThat(pista).isNotNull();
        assertEquals(1, pista.getId());
        assertEquals(1000, pista.getTamanho());
    }

    @Test
    @DisplayName("Buscar por ID ERROR")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void testFindByIdInexistente() {
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pistaService.findById(4));
        assertEquals("Pista ID 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Listar todos")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void testListAll() {
        List<Pista> lista = pistaService.listAll();
        assertEquals(3, lista.size());
    }
    
    @Test
    @DisplayName("Listar todos VOID")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void testListAllVoid() {
    	pistaService.delete(1);
    	pistaService.delete(2);
    	pistaService.delete(3);
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pistaService.listAll());
        assertEquals("Nenhuma pista cadastrada", exception.getMessage());
    }

    @Test
    @DisplayName("Cadastrar pista")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    void testSalvarPista() {
    	Pais pais = paisService.findById(2);
        Pista pista = pistaService.salvar(new Pista(null, 4000, pais));
        assertThat(pista).isNotNull();
        Pista pistaTest = pistaService.findById(1);
        assertEquals(1, pistaTest.getId());
        assertEquals(4000, pistaTest.getTamanho());
        assertEquals("Argentina", pistaTest.getPais().getName());
    }
    
    @Test
    @DisplayName("Cadastrar pista tamanho NULL")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    void testSalvarPistaNull() {
    	Pais pais = paisService.findById(2);
        var exception = assertThrows(ViolacaoIntegridade.class, () -> pistaService.salvar(new Pista(null, null, pais)));
        assertEquals("Tamanho inválido!", exception.getMessage());
    }
    
    @Test
    @DisplayName("Cadastrar pista tamanho ZERO")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    void testSalvarPistaZero() {
    	Pais pais = paisService.findById(2);
        var exception = assertThrows(ViolacaoIntegridade.class, () -> pistaService.salvar(new Pista(null, 0, pais)));
        assertEquals("Tamanho inválido!", exception.getMessage());
    }

    @Test
    @DisplayName("Update pista")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void testUpdate() {
    	Pais pais = paisService.findById(1);
        Pista pista = pistaService.update(new Pista(3, 4000, pais));
        assertThat(pista).isNotNull();
        Pista pistaTest = pistaService.findById(3);
        assertEquals(3, pistaTest.getId());
        assertEquals(4000, pistaTest.getTamanho());
        assertEquals("Brasil", pistaTest.getPais().getName());
    }
    
    @Test
    @DisplayName("Update pista ID ERROR")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void testUpdateIdError() {
    	Pais pais = paisService.findById(1);
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pistaService.update(new Pista(4, 4000, pais)));
        assertEquals("Pista ID 4 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Delete pista")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void testDelete() {
        pistaService.delete(2);
        List<Pista> lista = pistaService.listAll();
        assertEquals(2, lista.size());
    }
    
    @Test
    @DisplayName("Listar intervalo de tamanho")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void testFindByTamanhoBetween() {
        List<Pista> lista = pistaService.findByTamanhoBetween(2000, 4000);
        assertEquals(2, lista.size());
    }
    
    @Test
    @DisplayName("Listar intervalo de tamanho ERROR")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void testFindByTamanhoBetweenError() {
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pistaService.findByTamanhoBetween(4000, 6000));
        assertEquals("Nenhuma pista encontrada nesse intervalo de tamanho entre 4000 e 6000", exception.getMessage());
    }
    
    @Test
    @DisplayName("Listar por país")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void testFindByPais() {
    	List<Pista> lista = pistaService.findByPaisOrderByTamanhoDesc(paisService.findById(2));
        assertEquals(2, lista.size());
    }
    
    @Test
    @DisplayName("Listar por país ERROR")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    void testFindByPaisError() {
    	var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pistaService.findByPaisOrderByTamanhoDesc(paisService.findById(3)));
        assertEquals("Nenhuma pista no Uruguai", exception.getMessage());
    }

}