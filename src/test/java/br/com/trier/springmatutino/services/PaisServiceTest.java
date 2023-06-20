package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
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
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.findById(10));
		assertEquals("País 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void listAll() {
		var lista = paisService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("Listar todos void")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void listAllVoid() {
		paisService.delete(1);
		paisService.delete(2);
		paisService.delete(3);
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.listAll());
		assertEquals("Void!", exception.getMessage());
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
	@DisplayName("Cadastrar pais existente")
	void salvarExistente() {
		paisService.salvar(new Pais(null, "Chile"));
		var exception = assertThrows(ViolacaoIntegridade.class, () -> paisService.salvar(new Pais(null, "Chile")));
		assertEquals("País já cadastrado: Chile", exception.getMessage());
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
	@DisplayName("Update pais existente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updateExistenteTest() {
		var exception = assertThrows(ViolacaoIntegridade.class, () -> paisService.update(new Pais(2, "Brasil")));
		assertEquals("País já cadastrado: Brasil", exception.getMessage());
	}
	
	@Test
	@DisplayName("Update pais inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updateInexistenteTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.update(new Pais(10, "Peru")));
		assertEquals("País 10 não encontrado", exception.getMessage());
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
	@DisplayName("Delete pais inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deleteInexistenteTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> paisService.delete(10));
		assertEquals("País 10 não encontrado", exception.getMessage());
		List<Pais> lista = paisService.listAll();
		assertEquals(3, lista.size());
	}
	
	@Test
	@DisplayName("Buscar pais por nome")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNameIgnoreCaseTest() {
		List<Pais> lista = paisService.findByNameIgnoreCase("brasil");
		assertEquals(1, lista.size());
	}

	@Test
	@DisplayName("Buscar pais por nome/contains")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByNameContainsTest() {
		List<Pais> lista = paisService.findByNameContainsIgnoreCase("B");
		assertEquals(1, lista.size());
	}

}