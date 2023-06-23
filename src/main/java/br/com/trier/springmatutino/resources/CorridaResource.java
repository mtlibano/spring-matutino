package br.com.trier.springmatutino.resources;

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.domain.dto.CorridaDTO;
import br.com.trier.springmatutino.services.CampeonatoService;
import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.PistaService;
import br.com.trier.springmatutino.utils.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/corrida")
public class CorridaResource {

    @Autowired
    CorridaService service;
    
    @Autowired
    PistaService pistaService;
    
    @Autowired
    CampeonatoService campeonatoService;

    @PostMapping
    public ResponseEntity<CorridaDTO> insert(@RequestBody CorridaDTO corridaDTO) {
    	return ResponseEntity.ok(service.salvar(new Corrida(
    			corridaDTO,
    			pistaService.findById(corridaDTO.getPistaId()),
    			campeonatoService.findById(corridaDTO.getCampeonatoId())))
    			.toDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CorridaDTO> findById(@PathVariable Integer id) {
    	return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @GetMapping
    public ResponseEntity<List<CorridaDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(corrida -> corrida.toDTO()).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorridaDTO> update(@PathVariable Integer id, @RequestBody CorridaDTO corridaDTO) {
    	pistaService.findById(corridaDTO.getPistaId());
    	campeonatoService.findById(corridaDTO.getCampeonatoId());
    	Corrida corrida = new Corrida(corridaDTO);
        corrida.setId(id);
        corrida = service.update(corrida);
        return ResponseEntity.ok(corrida.toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/data/{data}")
    public ResponseEntity<List<CorridaDTO>> findByData(@PathVariable String data) {
        return ResponseEntity.ok(service.findByData(DateUtils.strToZonedDateTime(data)).stream().map(corrida -> corrida.toDTO()).toList());
    }
    
    @GetMapping("/data/{dataInicial}/{dataFinal}")
    public ResponseEntity<List<CorridaDTO>> findByDataBetween(@PathVariable String dataInicial, @PathVariable String dataFinal) {
        return ResponseEntity.ok(service.findByDataBetween(DateUtils.strToZonedDateTime(dataInicial), DateUtils.strToZonedDateTime(dataFinal)).stream().map(corrida -> corrida.toDTO()).toList());
    }
    
    @GetMapping("/pista/{idPista}")
    public ResponseEntity<List<CorridaDTO>> findByPista(@PathVariable Integer idPista) {
        return ResponseEntity.ok(service.findByPista(pistaService.findById(idPista)).stream().map(corrida -> corrida.toDTO()).toList());
    }
    
    @GetMapping("/campeonato/{idCampeonato}")
    public ResponseEntity<List<CorridaDTO>> findByCampeonato(@PathVariable Integer idCampeonato) {
        return ResponseEntity.ok(service.findByCampeonato(campeonatoService.findById(idCampeonato)).stream().map(corrida -> corrida.toDTO()).toList());
    }

}