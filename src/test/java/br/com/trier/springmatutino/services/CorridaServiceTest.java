package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.springmatutino.services.exceptions.ViolacaoIntegridade;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
public class CorridaServiceTest extends BaseTests{
	
	@Autowired
	CorridaService corridaService;
	
	@Autowired
	PistaService pistaService;
	
	@Autowired
	CampeonatoService campeonatoService;
	
	@Test
    @DisplayName("Buscar por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
    void testFindById() {
        Corrida corrida = corridaService.findById(1);
        assertThat(corrida).isNotNull();
        assertEquals(1, corrida.getId());
    }

    @Test
    @DisplayName("Buscar por ID ERROR")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
    void testFindByIdInexistente() {
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.findById(10));
        assertEquals("ID 10 inválido!", exception.getMessage());
    }

    @Test
    @DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
    void testListAll() {
        List<Corrida> lista = corridaService.listAll();
        assertEquals(4, lista.size());
    }
    
    @Test
    @DisplayName("Listar todos VOID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
    void testListAllVoid() {
    	corridaService.delete(1);
    	corridaService.delete(2);
    	corridaService.delete(3);
    	corridaService.delete(4);
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.listAll());
        assertEquals("Nenhuma corrida cadastrada!", exception.getMessage());
    }

    @Test
    @DisplayName("Cadastrar corrida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
    void testSalvarCorrida() {
    	ZonedDateTime data = ZonedDateTime.of(2023, 10, 10, 10, 0, 0, 0, ZoneId.systemDefault());
    	Pista pista = pistaService.findById(1);
    	Campeonato campeonato = campeonatoService.findById(1);
        Corrida corrida = corridaService.salvar(new Corrida(null, data, pista, campeonato));
        assertThat(corrida).isNotNull();
        Corrida corridaTest = corridaService.findById(1);
        assertEquals(1, corridaTest.getId());
        assertEquals(1, corridaTest.getPista().getId());
        assertEquals("Formula1", corridaTest.getCampeonato().getDescricao());
    }
    
    @Test
    @DisplayName("Cadastrar corrida data Error")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
    void testSalvarCorridaError() {
    	ZonedDateTime data = ZonedDateTime.of(2022, 6, 10, 10, 0, 0, 0, ZoneId.systemDefault());
    	Pista pista = pistaService.findById(1);
    	Campeonato campeonato = campeonatoService.findById(1);
        var exception = assertThrows(ViolacaoIntegridade.class, () -> corridaService.salvar(new Corrida(null, data, pista, campeonato)));
        assertEquals("Data inválida!", exception.getMessage());
    }

    @Test
    @DisplayName("Update corrida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
    void testUpdate() {
    	ZonedDateTime data = ZonedDateTime.of(2023, 10, 10, 10, 0, 0, 0, ZoneId.systemDefault());
    	Pista pista = pistaService.findById(1);
    	Campeonato campeonato = campeonatoService.findById(1);
        Corrida corrida = corridaService.update(new Corrida(2, data, pista, campeonato));
        assertThat(corrida).isNotNull();
        Corrida corridaTest = corridaService.findById(2);
        assertEquals(2, corridaTest.getId());
        assertEquals(1, corridaTest.getPista().getId());
        assertEquals("Formula1", corridaTest.getCampeonato().getDescricao());
    }
    
    @Test
    @DisplayName("Update corrida ID ERROR")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
    void testUpdateIdError() {
    	ZonedDateTime data = ZonedDateTime.of(2023, 6, 10, 10, 0, 0, 0, ZoneId.systemDefault());
    	Pista pista = pistaService.findById(1);
    	Campeonato campeonato = campeonatoService.findById(1);
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.update(new Corrida(10, data, pista, campeonato)));
        assertEquals("ID 10 inválido!", exception.getMessage());
    }

    @Test
    @DisplayName("Delete corrida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
    void testDelete() {
        corridaService.delete(2);
        List<Corrida> lista = corridaService.listAll();
        assertEquals(3, lista.size());
    }
    
    @Test
    @DisplayName("Listar por data")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
    void testFindByData() {
    	ZonedDateTime data = ZonedDateTime.of(2023, 10, 02, 10, 0, 0, 0, ZoneId.systemDefault());
        List<Corrida> lista = corridaService.findByData(data);
        assertEquals(1, lista.size());
    }
    
    @Test
    @DisplayName("Listar por data ERROR")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
    void testFindByDataError() {
    	ZonedDateTime data = ZonedDateTime.of(2022, 10, 10, 10, 0, 0, 0, ZoneId.systemDefault());
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.findByData(data));
        assertEquals("Nenhuma corrida na data: 2022-10-10T10:00-03:00[America/Sao_Paulo]", exception.getMessage());
    }
    
    @Test
    @DisplayName("Listar por pista")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
    void testFindByPista() {
    	List<Corrida> lista = corridaService.findByPista(pistaService.findById(2));
        assertEquals(2, lista.size());
    }
    
    @Test
    @DisplayName("Listar por pista ERROR")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
    void testFindByPistaError() {
    	var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.findByPista(pistaService.findById(3)));
        assertEquals("Nenhuma corrida na pista: 3", exception.getMessage());
    }
    
    @Test
    @DisplayName("Listar por campeonato")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
    void testFindByCampeonato() {
    	List<Corrida> lista = corridaService.findByCampeonato(campeonatoService.findById(2));
        assertEquals(2, lista.size());
    }
    
    @Test
    @DisplayName("Listar por campeonato ERROR")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
    void testFindByCampeonatoError() {
    	var exception = assertThrows(ObjetoNaoEncontrado.class, () -> corridaService.findByCampeonato(campeonatoService.findById(3)));
        assertEquals("Nenhuma corrida no campeonato: FormulaDrift", exception.getMessage());
    }

}