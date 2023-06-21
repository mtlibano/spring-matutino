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
//import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.springmatutino.SpringMatutinoApplication;
//import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.dto.PaisDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
//@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/resources/sqls/pais.sql")
//@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaisResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;
	
	private ResponseEntity<PaisDTO> getPais(String url) {
		return rest.getForEntity(url, PaisDTO.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<PaisDTO>> getPaisList(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<PaisDTO>>() {});
	}
	
	@Test
	@DisplayName("Buscar por id")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	public void testGetIdOk() {
		ResponseEntity<PaisDTO> response = getPais("/pais/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		PaisDTO pais = response.getBody();
		assertEquals(1, pais.getId());
		assertEquals("Brasil", pais.getName());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	public void testGetIdNotFound() {
		ResponseEntity<PaisDTO> response = getPais("/pais/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	public void testListAll() {
		ResponseEntity<List<PaisDTO>> responseEntity = rest.exchange(
				"/pais",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PaisDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<PaisDTO> list = responseEntity.getBody();
	    assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Cadastrar")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testCreatePais() {
		PaisDTO pais = new PaisDTO(null, "Peru");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PaisDTO> requestEntity = new HttpEntity<>(pais, headers);
		ResponseEntity<PaisDTO> responseEntity = rest.exchange(
				"/pais",
				HttpMethod.POST,
				requestEntity,
				PaisDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		PaisDTO newPais = responseEntity.getBody();
		assertEquals("Peru", newPais.getName());
	}
	
	@Test
	@DisplayName("Update")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	public  void testUpdate() {	    
		PaisDTO pais = new PaisDTO(3, "Peru");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PaisDTO> requestEntity = new HttpEntity<>(pais, headers);
		ResponseEntity<PaisDTO> responseEntity = rest.exchange(
				"/pais/3",
                HttpMethod.PUT,
                requestEntity,
                PaisDTO.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		PaisDTO updatedPais = responseEntity.getBody();
		assertThat(updatedPais).isNotNull();
	    assertThat(updatedPais.getId()).isEqualTo(3);
	    assertThat(updatedPais.getName()).isEqualTo("Peru");
	}
	
	@Test
	@DisplayName("Delete por ID")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	public void testDeleteId() {
		ResponseEntity<Void> responseEntity = rest.exchange(
                "/pais/1",
                HttpMethod.DELETE,
                null,
                Void.class,
                1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        
        ResponseEntity<List<PaisDTO>> responseEntityList = rest.exchange(
				"/pais",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PaisDTO>>() {}
        );
		assertEquals(responseEntityList.getStatusCode(), HttpStatus.OK);
		List<PaisDTO> list = responseEntityList.getBody();
	    assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Buscar por Nome IgnoreCase")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	public void testGetNameIgnoreCase() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<PaisDTO>> responseEntity = rest.exchange(
				"/pais/name/Brasil",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<PaisDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<PaisDTO> list = responseEntity.getBody();
	    assertEquals(1, list.size());
	    assertEquals(1, list.get(0).getId());
	    assertEquals("Brasil", list.get(0).getName());
	}
	
	@Test
	@DisplayName("Buscar por Nome Contains")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/pais.sql")
	public void testGetNameContains() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<PaisDTO>> responseEntity = rest.exchange(
				"/pais/name/contains/bra",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<PaisDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<PaisDTO> list = responseEntity.getBody();
	    assertEquals(1, list.size());
	    assertEquals(1, list.get(0).getId());
	    assertEquals("Brasil", list.get(0).getName());
	}	

}