package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Pais;
import jakarta.transaction.Transactional;

@Transactional
public class PaisServiceTest extends BaseTests {
	
	@Autowired
	PaisService paisService;
	
	@Test
	@DisplayName("Buscar pais por id")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByIdTest() {
		var pais = paisService.findById(1);
		assertThat(pais).isNotNull();
		assertEquals(1, pais.getId());
		assertEquals("Brasil", pais.getName());
	}
	
	@Test
	@DisplayName("Buscar pais por id inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByIdTestInexistente() {
		var pais = paisService.findById(4);
		assertThat(pais).isNull();
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void listAll() {
		var lista = paisService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("Cadastrar pais")
	void salvar() {
		Pais pais = paisService.salvar(new Pais(null, "Chile"));
		assertThat(pais).isNotNull();
		var paisTest = paisService.findById(1);
		assertEquals(1, paisTest.getId());
		assertEquals("Chile", paisTest.getName());
	}
	
	@Test
	@DisplayName("Update pais")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updateTest() {
		Pais pais = paisService.update(new Pais(2, "Chile"));
		assertThat(pais).isNotNull();
		var paisTest = paisService.findById(2);
		assertEquals(2, paisTest.getId());
		assertEquals("Chile", paisTest.getName());
	}
	
	@Test
	@DisplayName("Delete pais")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deleteTest() {
		paisService.delete(2);
		List<Pais> lista = paisService.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Buscar pais por nome")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNameTest() {
		List<Pais> lista = paisService.findByName("Brasil");
		assertEquals(1, lista.size());
		lista = paisService.findByName("Argentina");
		assertEquals(1, lista.size());
	}

}