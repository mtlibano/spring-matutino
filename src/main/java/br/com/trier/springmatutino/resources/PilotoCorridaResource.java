package br.com.trier.springmatutino.resources;

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.PilotoCorrida;
import br.com.trier.springmatutino.domain.dto.PilotoCorridaDTO;
import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.PilotoCorridaService;
import br.com.trier.springmatutino.services.PilotoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/piloto-corrida")
public class PilotoCorridaResource {

    @Autowired
    PilotoCorridaService service;
    
    @Autowired
    PilotoService pilotoService;
    
    @Autowired
    CorridaService corridaService;

    @PostMapping
    public ResponseEntity<PilotoCorridaDTO> insert(@RequestBody PilotoCorridaDTO pilotoCorridaDTO) {
    	return ResponseEntity.ok(service.salvar(new PilotoCorrida(
    			pilotoCorridaDTO,
    			pilotoService.findById(pilotoCorridaDTO.getIdPiloto()),
    			corridaService.findById(pilotoCorridaDTO.getIdCorrida())))
    			.toDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PilotoCorridaDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @GetMapping
    public ResponseEntity<List<PilotoCorridaDTO>> listAll() {
    	return ResponseEntity.ok(service.listAll().stream().map(pilotoCorrida -> pilotoCorrida.toDTO()).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PilotoCorridaDTO> update(@PathVariable Integer id, @RequestBody PilotoCorridaDTO pilotoCorridaDTO) {
    	Piloto piloto = pilotoService.findById(pilotoCorridaDTO.getIdPiloto());
    	Corrida corrida = corridaService.findById(pilotoCorridaDTO.getIdCorrida());
    	PilotoCorrida pilotoCorrida = new PilotoCorrida(pilotoCorridaDTO, piloto, corrida);
    	pilotoCorrida.setId(id);
    	pilotoCorrida = service.update(pilotoCorrida);
        return ResponseEntity.ok(pilotoCorrida.toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/colocacao/{colocacao}")
    public ResponseEntity<List<PilotoCorridaDTO>> findByColocacao(@PathVariable Integer colocacao) {
        return ResponseEntity.ok(service.findByColocacao(colocacao).stream().map(pilotoCorrida -> pilotoCorrida.toDTO()).toList());
    }
    
    @GetMapping("/piloto/{idPiloto}")
    public ResponseEntity<List<PilotoCorridaDTO>> findByPiloto(@PathVariable Integer idPiloto) {
        return ResponseEntity.ok(service.findByPiloto(pilotoService.findById(idPiloto)).stream().map(pilotoCorrida -> pilotoCorrida.toDTO()).toList());
    }
    
    @GetMapping("/corrida/{idCorrida}")
    public ResponseEntity<List<PilotoCorridaDTO>> findByCorridaOrderByColocacaoAsc(@PathVariable Integer idCorrida) {
        return ResponseEntity.ok(service.findByCorridaOrderByColocacaoAsc(corridaService.findById(idCorrida)).stream().map(pilotoCorrida -> pilotoCorrida.toDTO()).toList());
    }
    
    @GetMapping("/colocacao-corrida/{colocacaoInicial}/{colocacaoFinal}/{idCorrida}")
    public ResponseEntity<List<PilotoCorridaDTO>> findByColocacaoBetweenAndCorrida(@PathVariable Integer colocacaoInicial, @PathVariable Integer colocacaoFinal, @PathVariable Integer idCorrida) {
        return ResponseEntity.ok(
        		service.findByColocacaoBetweenAndCorrida(colocacaoInicial, colocacaoFinal, corridaService.findById(idCorrida)).stream().map(pilotoCorrida -> pilotoCorrida.toDTO()).toList());
    }
    
    @GetMapping("/piloto-corrida/{idPiloto}/{idCorrida}")
    public ResponseEntity<PilotoCorridaDTO> findByPilotoAndCorrida(@PathVariable Integer idPiloto, @PathVariable Integer idCorrida) {
    	return ResponseEntity.ok(service.findByPilotoAndCorrida(pilotoService.findById(idPiloto), corridaService.findById(idCorrida)).toDTO());
    }

}