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
//import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.dto.CampeonatoDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
//@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/resources/sqls/campeonato.sql")
//@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CampeonatoResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;
	
	private ResponseEntity<CampeonatoDTO> getCampeonato(String url) {
		return rest.getForEntity(url, CampeonatoDTO.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<CampeonatoDTO>> getCampeonatoList(String url) {
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CampeonatoDTO>>() {});
	}
	
	@Test
	@DisplayName("Buscar por id")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/campeonato.sql")
	public void testGetIdOk() {
		ResponseEntity<CampeonatoDTO> response = getCampeonato("/campeonato/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		CampeonatoDTO campeonato = response.getBody();
		assertEquals(1, campeonato.getId());
		assertEquals("Formula1", campeonato.getDescricao());
		assertEquals(2020, campeonato.getAno());
	}

	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/campeonato.sql")
	public void testGetIdNotFound() {
		ResponseEntity<CampeonatoDTO> response = getCampeonato("/campeonato/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Listar todos")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/campeonato.sql")
	public void testListAll() {
		ResponseEntity<List<CampeonatoDTO>> responseEntity = rest.exchange(
				"/campeonato",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CampeonatoDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<CampeonatoDTO> list = responseEntity.getBody();
	    assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Cadastrar campeonato")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	public void testCreateUser() {
		CampeonatoDTO campeonato = new CampeonatoDTO(null, "Ultimate", 2023);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CampeonatoDTO> requestEntity = new HttpEntity<>(campeonato, headers);
		ResponseEntity<CampeonatoDTO> responseEntity = rest.exchange(
				"/campeonato",
				HttpMethod.POST,
				requestEntity,
				CampeonatoDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		CampeonatoDTO newCamp = responseEntity.getBody();
		assertEquals("Ultimate", newCamp.getDescricao());
	}
	
	@Test
	@DisplayName("Update")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/campeonato.sql")
	public  void testUpdate() {	    
		CampeonatoDTO campeonato = new CampeonatoDTO(3, "Ultimate", 2023);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CampeonatoDTO> requestEntity = new HttpEntity<>(campeonato, headers);
		ResponseEntity<CampeonatoDTO> responseEntity = rest.exchange(
				"/campeonato/3",
                HttpMethod.PUT,
                requestEntity,
                CampeonatoDTO.class
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		CampeonatoDTO updatedCamp = responseEntity.getBody();
		assertThat(updatedCamp).isNotNull();
	    assertThat(updatedCamp.getId()).isEqualTo(3);
	    assertThat(updatedCamp.getDescricao()).isEqualTo("Ultimate");
	}
	
	@Test
	@DisplayName("Delete por ID")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/campeonato.sql")
	public void testDeleteId() {
		ResponseEntity<Void> responseEntity = rest.exchange(
                "/campeonato/1",
                HttpMethod.DELETE,
                null,
                Void.class,
                1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        
        ResponseEntity<List<CampeonatoDTO>> responseEntityList = rest.exchange(
				"/campeonato",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CampeonatoDTO>>() {}
        );
		assertEquals(responseEntityList.getStatusCode(), HttpStatus.OK);
		List<CampeonatoDTO> list = responseEntityList.getBody();
	    assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Buscar por Descricao IgnoreCase")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/campeonato.sql")
	public void testDescricaoName() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<CampeonatoDTO>> responseEntity = rest.exchange(
				"/campeonato/descricao/formula1",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<CampeonatoDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<CampeonatoDTO> list = responseEntity.getBody();
	    assertEquals(1, list.size());
	    assertEquals(1, list.get(0).getId());
	    assertEquals("Formula1", list.get(0).getDescricao());
	}
	
	@Test
	@DisplayName("Buscar por Descricao Contains")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/campeonato.sql")
	public void testGetDescricaoContains() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<CampeonatoDTO>> responseEntity = rest.exchange(
				"/campeonato/descricao/contains/nas",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<CampeonatoDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<CampeonatoDTO> list = responseEntity.getBody();
	    assertEquals(1, list.size());
	    assertEquals(2, list.get(0).getId());
	    assertEquals("Nascar", list.get(0).getDescricao());
	}
	
	@Test
	@DisplayName("Buscar por Ano")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/campeonato.sql")
	public void testFindAno() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<CampeonatoDTO>> responseEntity = rest.exchange(
				"/campeonato/ano/2022",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<CampeonatoDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<CampeonatoDTO> list = responseEntity.getBody();
	    assertEquals(1, list.size());
	    assertEquals(3, list.get(0).getId());
	    assertEquals("FormulaDrift", list.get(0).getDescricao());
	}
	
	@Test
	@DisplayName("Buscar por Intervalo de Anos")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/campeonato.sql")
	public void testFindAnoBetween() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<CampeonatoDTO>> responseEntity = rest.exchange(
				"/campeonato/ano/2021/2022",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<CampeonatoDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<CampeonatoDTO> list = responseEntity.getBody();
	    assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Buscar por Descricao e Ano")
	@Sql(scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	@Sql(scripts = "classpath:/resources/sqls/campeonato.sql")
	public void testFindDescricaoAno() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<List<CampeonatoDTO>> responseEntity = rest.exchange(
				"/campeonato/descricao-ano/Formula1/2020",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<CampeonatoDTO>>() {}
        );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		List<CampeonatoDTO> list = responseEntity.getBody();
	    assertEquals(1, list.size());
	    assertEquals(1, list.get(0).getId());
	    assertEquals("Formula1", list.get(0).getDescricao());
	}

}