package br.com.trier.springmatutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.User;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests {
	
	@Autowired
	UserService userService;
	
	@Test
	@DisplayName("Buscar user por id")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdTest() {
		var usuario = userService.findById(1);
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("maxtest", usuario.getName());
		assertEquals("max@email.com", usuario.getEmail());
		assertEquals("1234", usuario.getPassword());
	}
	
	@Test
	@DisplayName("Buscar user por id inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdTestInexistente() {
		var usuario = userService.findById(3);
		assertThat(usuario).isNull();
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void listAll() {
		var lista = userService.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Cadastrar user")
	void salvar() {
		User usuario = userService.salvar(new User(null, "mari", "mari@email.com", "1234"));
		assertThat(usuario).isNotNull();
		assertEquals(1, usuario.getId());
		assertEquals("mari", usuario.getName());
		assertEquals("mari@email.com", usuario.getEmail());
		assertEquals("1234", usuario.getPassword());
	}
	
	@Test
	@DisplayName("Update user")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void updateUser() {
		User usuario = userService.update(new User(2, "mari", "mari@email.com", "1234"));
		assertThat(usuario).isNotNull();
		var userTest = userService.findById(2);
		assertEquals(2, userTest.getId());
		assertEquals("mari", userTest.getName());
		assertEquals("mari@email.com", userTest.getEmail());
		assertEquals("1234", userTest.getPassword());
	}
	
	@Test
	@DisplayName("Delete user")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void deleteUser() {
		userService.delete(2);
		List<User> lista = userService.listAll();
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Buscar user por nome")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByName() {
		List<User> lista = userService.findByName("maxtest");
		assertEquals(1, lista.size());
		lista = userService.findByName("nikitest");
		assertEquals(1, lista.size());
	}
	
	
	

}
