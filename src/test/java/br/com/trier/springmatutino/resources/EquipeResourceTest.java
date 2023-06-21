package br.com.trier.springmatutino.resources;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.springmatutino.SpringMatutinoApplication;
//import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.dto.EquipeDTO;


@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/resources/sqls/equipe.sql")
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EquipeResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;
	
	private ResponseEntity<EquipeDTO> getEquipe(String url) {
		return rest.getForEntity(url, EquipeDTO.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<EquipeDTO>> getEquipeList(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<EquipeDTO>>() {});
	}
	
	@Test
	@DisplayName("Buscar por id")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/equipe.sql")
	public void testGetIdOk() {
		ResponseEntity<EquipeDTO> response = getEquipe("/equipe/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		EquipeDTO equipe = response.getBody();
		assertEquals(1, equipe.getId());
		assertEquals("Mercedez", equipe.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/equipe.sql")
	public void testGetIdNotFound() {
		ResponseEntity<EquipeDTO> response = getEquipe("/equipe/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/equipe.sql")
	public void testListAll() {
		ResponseEntity<List<EquipeDTO>> responseEntity = rest.exchange(
				"/equipe",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<EquipeDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<EquipeDTO> list = responseEntity.getBody();
	    assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Cadastrar equipe")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testCreateUser() {
		EquipeDTO equipe = new EquipeDTO(null, "Ferrari");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EquipeDTO> requestEntity = new HttpEntity<>(equipe, headers);
		ResponseEntity<EquipeDTO> responseEntity = rest.exchange(
				"/equipe",
				HttpMethod.POST,
				requestEntity,
				EquipeDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		EquipeDTO newEquipe = responseEntity.getBody();
		assertEquals("Ferrari", newEquipe.getName());
	}
	
	@Test
	@DisplayName("Update")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/equipe.sql")
	public  void testUpdate() {	    
		EquipeDTO equipe = new EquipeDTO(3, "Ferrari");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<EquipeDTO> requestEntity = new HttpEntity<>(equipe, headers);
		ResponseEntity<EquipeDTO> responseEntity = rest.exchange(
				"/equipe/3",
                HttpMethod.PUT,
                requestEntity,
                EquipeDTO.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		EquipeDTO updatedEquipe = responseEntity.getBody();
		assertThat(updatedEquipe).isNotNull();
	    assertThat(updatedEquipe.getId()).isEqualTo(3);
	    assertThat(updatedEquipe.getName()).isEqualTo("Ferrari");
	}
	
	@Test
	@DisplayName("Delete por ID")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/equipe.sql")
	public void testDeleteId() {
		ResponseEntity<Void> responseEntity = rest.exchange(
                "/equipe/1",
                HttpMethod.DELETE,
                null,
                Void.class,
                1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        
        ResponseEntity<List<EquipeDTO>> responseEntityList = rest.exchange(
				"/equipe",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<EquipeDTO>>() {}
        );
		assertEquals(responseEntityList.getStatusCode(), HttpStatus.OK);
		List<EquipeDTO> list = responseEntityList.getBody();
	    assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Buscar por Nome IgnoreCase")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/equipe.sql")
	public void testGetName() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<EquipeDTO>> responseEntity = rest.exchange(
				"/equipe/name/mercedez",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<EquipeDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<EquipeDTO> list = responseEntity.getBody();
	    assertEquals(1, list.size());
	    assertEquals(1, list.get(0).getId());
	    assertEquals("Mercedez", list.get(0).getName());
	}
	
	@Test
	@DisplayName("Buscar por Nome Contains")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/equipe.sql")
	public void testGetNameContains() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<EquipeDTO>> responseEntity = rest.exchange(
				"/equipe/name/contains/merc",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<EquipeDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<EquipeDTO> list = responseEntity.getBody();
	    assertEquals(1, list.size());
	    assertEquals(1, list.get(0).getId());
	    assertEquals("Mercedez", list.get(0).getName());
	}

}