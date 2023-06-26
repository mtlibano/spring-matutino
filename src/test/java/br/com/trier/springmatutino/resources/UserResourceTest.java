package br.com.trier.springmatutino.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
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
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.springmatutino.SpringMatutinoApplication;
import br.com.trier.springmatutino.config.jwt.JwtAuthFilter;
import br.com.trier.springmatutino.config.jwt.JwtUserDetailService;
import br.com.trier.springmatutino.config.jwt.JwtUtil;
import br.com.trier.springmatutino.config.jwt.LoginDTO;
import br.com.trier.springmatutino.domain.dto.UserDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/resources/sqls/usuario.sql")
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;
	
	@Autowired
	protected JwtResource service;

	private ResponseEntity<UserDTO> getUser(String url) {
		return rest.getForEntity(url, UserDTO.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<UserDTO>> getUsers(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {});
	}
	
	@Test
	@DisplayName("Buscar por id")
	public void testGetIdOk() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		UserDTO user = response.getBody();
		assertEquals("max", user.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	public void testGetIdNotFound() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Listar todos")
	public void testListAll() {
		ResponseEntity<List<UserDTO>> responseEntity = rest.exchange(
				"/usuarios",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<UserDTO> users = responseEntity.getBody();
	    assertEquals(2, users.size());
	}
	
	public String getToken() {
		LoginDTO loginDTO = new LoginDTO("email", "senha");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token",
				HttpMethod.POST,
				requestEntity,
				UserDTO.class);
		
		
		
	}
	
	@Test
	@DisplayName("Cadastrar usuário")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "nome", "email", "senha", "ADMIN");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		
		String token = service.authenticateAndGetToken(new LoginDTO("max", "1234"));
		headers.add("Authorization", "Bearer " + token);
		//headers.add("Authorization", "Bearer " + token);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
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
	@DisplayName("Delete por ID")
	public void testDeleteId() {
		ResponseEntity<Void> responseEntity = rest.exchange(
                "/usuarios/1",
                HttpMethod.DELETE,
                null,
                Void.class,
                1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        
        ResponseEntity<List<UserDTO>> responseEntityList = rest.exchange(
				"/usuarios",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserDTO>>() {}
        );
		assertEquals(responseEntityList.getStatusCode(), HttpStatus.OK);
		List<UserDTO> users = responseEntityList.getBody();
	    assertEquals(1, users.size());
	}
	
	@Test
	@DisplayName("Buscar por Nome")
	public void testGetNameOk() {
		ResponseEntity<UserDTO> response = getUser("/usuarios/name/max");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		UserDTO user = response.getBody();
		assertEquals("max", user.getName());
	}
	
	@Test
	@DisplayName("Buscar por Nome ERROR")
	public void testGetNameError() {
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
                "/usuarios/name/max",
                HttpMethod.GET,
                null,
                UserDTO.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        
        UserDTO user = responseEntity.getBody();
		assertEquals("max", user.getName());
	}

}