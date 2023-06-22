package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.Piloto;
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
public class PilotoServiceTest extends BaseTests {
	
	@Autowired
	PilotoService pilotoService;
	
	@Autowired
	EquipeService equipeService;
	
	@Autowired
	PaisService paisService;
	
	@Test
    @DisplayName("Buscar por ID")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testFindById() {
        Piloto piloto = pilotoService.findById(1);
        assertThat(piloto).isNotNull();
        assertEquals(1, piloto.getId());
        assertEquals("Max", piloto.getName());
    }

    @Test
    @DisplayName("Buscar por ID ERROR")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testFindByIdInexistente() {
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.findById(10));
        assertEquals("ID 10 inválido!", exception.getMessage());
    }

    @Test
    @DisplayName("Listar todos")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testListAll() {
        List<Piloto> lista = pilotoService.listAll();
        assertEquals(4, lista.size());
    }
    
    @Test
    @DisplayName("Listar todos VOID")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testListAllVoid() {
    	pilotoService.delete(1);
    	pilotoService.delete(2);
    	pilotoService.delete(3);
    	pilotoService.delete(4);
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.listAll());
        assertEquals("Nenhum piloto cadastrado!", exception.getMessage());
    }

    @Test
    @DisplayName("Cadastrar piloto")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    void testSalvarPiloto() {
    	Equipe equipe = equipeService.findById(1);
    	Pais pais = paisService.findById(2);
        Piloto piloto = pilotoService.salvar(new Piloto(null, "Mari", equipe, pais));
        assertThat(piloto).isNotNull();
        Piloto pilotoTest = pilotoService.findById(1);
        assertEquals(1, pilotoTest.getId());
        assertEquals("Mari", pilotoTest.getName());
        assertEquals("Mercedez", pilotoTest.getEquipe().getName());
        assertEquals("Argentina", pilotoTest.getPais().getName());
    }
    
    @Test
    @DisplayName("Cadastrar piloto NULL")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    void testSalvarPilotoNull() {
    	Equipe equipe = equipeService.findById(1);
    	Pais pais = paisService.findById(2);
        var exception = assertThrows(ViolacaoIntegridade.class, () -> pilotoService.salvar(new Piloto(null, null, equipe, pais)));
        assertEquals("Nome piloto null!", exception.getMessage());
    }

    @Test
    @DisplayName("Update piloto")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testUpdate() {
    	Equipe equipe = equipeService.findById(1);
    	Pais pais = paisService.findById(1);
        Piloto piloto = pilotoService.update(new Piloto(4, "Mari", equipe, pais));
        assertThat(piloto).isNotNull();
        Piloto pilotoTest = pilotoService.findById(4);
        assertEquals(4, pilotoTest.getId());
        assertEquals("Mercedez", pilotoTest.getEquipe().getName());
        assertEquals("Brasil", pilotoTest.getPais().getName());
    }
    
    @Test
    @DisplayName("Update piloto ID ERROR")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testUpdateIdError() {
    	Equipe equipe = equipeService.findById(1);
    	Pais pais = paisService.findById(1);
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.update(new Piloto(10, "Max", equipe, pais)));
        assertEquals("ID 10 inválido!", exception.getMessage());
    }

    @Test
    @DisplayName("Delete piloto")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testDelete() {
        pilotoService.delete(2);
        List<Piloto> lista = pilotoService.listAll();
        assertEquals(3, lista.size());
    }
    
    @Test
    @DisplayName("Listar por nome")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testFindByNameIgnoreCase() {
        List<Piloto> lista = pilotoService.findByNameIgnoreCase("max");
        assertEquals(1, lista.size());
    }
    
    @Test
    @DisplayName("Listar por nome ERROR")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testFindByNameIgnoreCaseError() {
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.findByNameIgnoreCase("bia"));
        assertEquals("Nenhum piloto com o nome: bia", exception.getMessage());
    }
    
    @Test
    @DisplayName("Listar por país")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testFindByPais() {
    	List<Piloto> lista = pilotoService.findByPaisOrderByNameDesc(paisService.findById(2));
        assertEquals(3, lista.size());
    }
    
    @Test
    @DisplayName("Listar por país ERROR")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testFindByPaisError() {
    	var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.findByPaisOrderByNameDesc(paisService.findById(3)));
        assertEquals("Nenhum piloto do país: Uruguai", exception.getMessage());
    }
    
    @Test
    @DisplayName("Listar por equipe")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testFindByEquipe() {
    	List<Piloto> lista = pilotoService.findByEquipeOrderByNameDesc(equipeService.findById(1));
        assertEquals(2, lista.size());
    }
    
    @Test
    @DisplayName("Listar por equipe ERROR")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testFindByEquipeError() {
    	var exception = assertThrows(ObjetoNaoEncontrado.class, () -> pilotoService.findByEquipeOrderByNameDesc(equipeService.findById(3)));
        assertEquals("Nenhum piloto da equipe: RedBull", exception.getMessage());
    }
 
}