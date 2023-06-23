package br.com.trier.springmatutino.services;

import br.com.trier.springmatutino.BaseTests;
import br.com.trier.springmatutino.domain.PilotoCorrida;
import br.com.trier.springmatutino.domain.Corrida;
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
public class PilotoCorridaServiceTest extends BaseTests {
	
	@Autowired
	PilotoCorridaService service;
	
	@Autowired
	CorridaService corridaService;
	
	@Autowired
	PilotoService pilotoService;
	
	@Test
    @DisplayName("Buscar por ID")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
    void testFindById() {
        PilotoCorrida pilotoCorrida = service.findById(1);
        assertThat(pilotoCorrida).isNotNull();
        assertEquals(1, pilotoCorrida.getId());
        assertEquals(1, pilotoCorrida.getCorrida().getId());
        assertEquals(1, pilotoCorrida.getPiloto().getId());
    }

    @Test
    @DisplayName("Buscar por ID ERROR")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
    void testFindByIdInexistente() {
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(10));
        assertEquals("ID 10 inválido!", exception.getMessage());
    }

    @Test
    @DisplayName("Listar todos")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
    void testListAll() {
        List<PilotoCorrida> lista = service.listAll();
        assertEquals(6, lista.size());
    }
    
    @Test
    @DisplayName("Listar todos VOID")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
    void testListAllVoid() {
    	service.delete(1);
    	service.delete(2);
    	service.delete(3);
    	service.delete(4);
    	service.delete(5);
    	service.delete(6);
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.listAll());
        assertEquals("Nenhum PilotoCorrida cadastrado!", exception.getMessage());
    }

    @Test
    @DisplayName("Cadastrar PilotoCorrida")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testSalvarPilotoCorrida() {
    	Corrida corrida = corridaService.findById(1);
    	Piloto piloto = pilotoService.findById(1);
        PilotoCorrida pilotoCorrida = service.salvar(new PilotoCorrida(null, 4, piloto, corrida));
        assertThat(pilotoCorrida).isNotNull();
        PilotoCorrida pilotoCorridaTest = service.findById(1);
        assertEquals(1, pilotoCorridaTest.getId());
        assertEquals(4, pilotoCorridaTest.getColocacao());
        assertEquals("Max", pilotoCorridaTest.getPiloto().getName());
    }
    
    @Test
    @DisplayName("Cadastrar PilotoCorrida Null")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testSalvarPilotoCorridaNull() {
    	Corrida corrida = corridaService.findById(1);
    	Piloto piloto = pilotoService.findById(1);
    	var exception = assertThrows(ViolacaoIntegridade.class, () -> service.salvar(new PilotoCorrida(null, null, piloto, corrida)));
        assertEquals("Colocacao null!", exception.getMessage());
    }
    
    @Test
    @DisplayName("Cadastrar PilotoCorrida Zero")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
    void testSalvarPilotoCorridaZero() {
    	Corrida corrida = corridaService.findById(1);
    	Piloto piloto = pilotoService.findById(1);
    	var exception = assertThrows(ViolacaoIntegridade.class, () -> service.salvar(new PilotoCorrida(null, 0, piloto, corrida)));
        assertEquals("Colocacao zero!", exception.getMessage());
    }
    
    @Test
    @DisplayName("Update PilotoCorrida")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
    void testUpdate() {
    	Corrida corrida = corridaService.findById(1);
    	Piloto piloto = pilotoService.findById(2);
    	PilotoCorrida pilotoCorrida = service.update(new PilotoCorrida(2, 4, piloto, corrida));
        assertThat(pilotoCorrida).isNotNull();
        PilotoCorrida pilotoCorridaTest = service.findById(2);
        assertEquals(2, pilotoCorridaTest.getId());
        assertEquals(2, pilotoCorridaTest.getPiloto().getId());
        assertEquals(4, pilotoCorridaTest.getColocacao());
    }
    
    @Test
    @DisplayName("Delete PilotoCorrida")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
    void testDelete() {
        service.delete(2);
        List<PilotoCorrida> lista = service.listAll();
        assertEquals(5, lista.size());
    }
    
    @Test
    @DisplayName("Listar por Colocacao")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
    void testFindByColocacao() {
        List<PilotoCorrida> lista = service.findByColocacao(2);
        assertEquals(2, lista.size());
    }
    
    @Test
    @DisplayName("Listar por Colocacao ERROR")
	@Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
    void testFindByColocacaoError() {
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByColocacao(4));
        assertEquals("Nenhum PilotoCorrida nessa colocacao: 4", exception.getMessage());
    }
    
    @Test
    @DisplayName("Listar por Piloto")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
    void testFindByPiloto() {
    	List<PilotoCorrida> lista = service.findByPiloto(pilotoService.findById(1));
        assertEquals(2, lista.size());
        assertEquals(1, lista.get(0).getColocacao());
        assertEquals(3, lista.get(1).getColocacao());
    }
    
    @Test
    @DisplayName("Listar por Piloto ERROR")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
    void testFindByPilotoError() {
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByPiloto(pilotoService.findById(4)));
        assertEquals("Nenhum PilotoCorrida com esse piloto: 4", exception.getMessage());
    }
    
    @Test
    @DisplayName("Listar por Corrida")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
    void testFindByCorrida() {
    	List<PilotoCorrida> lista = service.findByCorridaOrderByColocacaoAsc(corridaService.findById(1));
        assertEquals(3, lista.size());
        assertEquals("Max", lista.get(0).getPiloto().getName());
        assertEquals("Niki", lista.get(1).getPiloto().getName());
        assertEquals("Pedro", lista.get(2).getPiloto().getName());
    }
    
    @Test
    @DisplayName("Listar por Corrida ERROR")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
    void testFindByCorridaError() {
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByCorridaOrderByColocacaoAsc(corridaService.findById(4)));
        assertEquals("Nenhum PilotoCorrida nessa corrida: 4", exception.getMessage());
    }
    
    @Test
    @DisplayName("Listar por Intervalo Colocacao e Corrida")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
    void testFindByColocacaoBetweenAndCorrida() {
    	List<PilotoCorrida> lista = service.findByColocacaoBetweenAndCorrida(1, 2, corridaService.findById(1));
        assertEquals(2, lista.size());
        assertEquals("Max", lista.get(0).getPiloto().getName());
        assertEquals("Niki", lista.get(1).getPiloto().getName());
    }
    
    @Test
    @DisplayName("Listar por Intervalo Colocacao e Corrida ERROR")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
    void testFindByColocacaoBetweenAndCorridError() {
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByColocacaoBetweenAndCorrida(1, 2, corridaService.findById(4)));
        assertEquals("Nenhum PilotoCorrida nessa corrida: 4, nesse intervalo de colocacao: 1 e 2", exception.getMessage());
    }
    
    @Test
    @DisplayName("Listar por Piloto e Corrida")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
    void testFindByPilotoAndCorrida() {
    	PilotoCorrida pilotoCorrida = service.findByPilotoAndCorrida(pilotoService.findById(1), corridaService.findById(1));
        assertEquals("Max", pilotoCorrida.getPiloto().getName());
        assertEquals("Formula1", pilotoCorrida.getCorrida().getCampeonato().getDescricao());
    }
    
    @Test
    @DisplayName("Listar por Piloto e Corrida ERROR")
    @Sql({"classpath:/resources/sqls/pais.sql"})
    @Sql({"classpath:/resources/sqls/equipe.sql"})
    @Sql({"classpath:/resources/sqls/pista.sql"})
    @Sql({"classpath:/resources/sqls/campeonato.sql"})
	@Sql({"classpath:/resources/sqls/corrida.sql"})
	@Sql({"classpath:/resources/sqls/piloto.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
    void testFindByPilotoAndCorridaError() {
        var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByPilotoAndCorrida(pilotoService.findById(4), corridaService.findById(4)));
        assertEquals("Nenhum PilotoCorrida com esse piloto: 4, nessa corrida: 4", exception.getMessage());
    }

}