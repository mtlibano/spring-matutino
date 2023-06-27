package br.com.trier.springmatutino.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springmatutino.SpringMatutinoApplication;
import br.com.trier.springmatutino.config.jwt.LoginDTO;
import br.com.trier.springmatutino.domain.dto.UserDTO;

@ActiveProfiles("test")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;

	private HttpHeaders getHeaders(String email, String password) {
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST, 
				requestEntity, 
				String.class);
		
		String token = responseEntity.getBody();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		return headers;
	}
	
	private ResponseEntity<UserDTO> getUser(String url) {
		return rest.exchange(
				url, 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("max@email.com", "1234")),
				UserDTO.class
				);
	}
	
	private ResponseEntity<List<UserDTO>> getUsers(String url) {
		return rest.exchange(
				url, 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("max@email.com", "1234")),
				new ParameterizedTypeReference<List<UserDTO>>() {}
				);
	}
	
	@Test
	@DisplayName("Buscar por id")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/usuario.sql")
	public void testGetIdOk() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/3");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		UserDTO user = response.getBody();
		assertEquals("max", user.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/usuario.sql")
	public void testGetIdNotFound() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/usuario.sql")
	public void testListAll() {
		ResponseEntity<List<UserDTO>> responseEntity = rest.exchange(
				"/usuarios",
                HttpMethod.GET,
                new HttpEntity<>(getHeaders("max@email.com", "1234")),
                new ParameterizedTypeReference<List<UserDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	    assertEquals(2, responseEntity.getBody().size());
	}
	
	@Test
	@DisplayName("Cadastrar usuário")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/usuario.sql")
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "nome", "email", "senha", "ADMIN");
		HttpHeaders headers = getHeaders("max@email.com", "1234");
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		headers.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/usuarios",
				HttpMethod.POST,
				requestEntity,
				UserDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("nome", user.getName());
	}
	
	@Test
	@DisplayName("Alterar usuário")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	public void testUpdateUser() {
		UserDTO dto = new UserDTO(4, "nome", "email", "senha", "ADMIN");
		HttpHeaders headers = getHeaders("max@email.com", "1234");
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/usuarios/4", 
				HttpMethod.PUT,  
				requestEntity,    
				UserDTO.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		UserDTO user = responseEntity.getBody();
		assertEquals("nome", user.getName());
	}
	
	@Test
	@DisplayName("Delete por ID")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/usuario.sql")
	public void testDeleteId() {
		HttpHeaders headers = getHeaders("max@email.com", "1234");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> responseEntity = rest.exchange(
				"/usuarios/4", 
				HttpMethod.DELETE,  
				requestEntity, 
				Void.class
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Buscar por Nome")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/usuario.sql")
	public void testGetNameOk() {
		ResponseEntity<List<UserDTO>> response = getUsers("/usuarios/name-list/max");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("max", response.getBody().get(0).getName());
	}

}