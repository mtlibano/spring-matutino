package br.com.trier.springmatutino.resources;

import br.com.trier.springmatutino.domain.Piloto;
import br.com.trier.springmatutino.domain.dto.PilotoDTO;
import br.com.trier.springmatutino.services.EquipeService;
import br.com.trier.springmatutino.services.PaisService;
import br.com.trier.springmatutino.services.PilotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/piloto")
public class PilotoResource {

    @Autowired
    PilotoService service;
    
    @Autowired
    PaisService paisService;
    
    @Autowired
    EquipeService equipeService;

    @PostMapping
    public ResponseEntity<PilotoDTO> insert(@RequestBody PilotoDTO piloto) {
    	paisService.findById(piloto.getPais().getId());
    	equipeService.findById(piloto.getEquipe().getId());
    	Piloto newPiloto = service.salvar(new Piloto(piloto));
        return ResponseEntity.ok(newPiloto.toDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PilotoDTO> findById(@PathVariable Integer id) {
        Piloto piloto = service.findById(id);
    	return ResponseEntity.ok(piloto.toDto());
    }

    @GetMapping
    public ResponseEntity<List<PilotoDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(piloto -> piloto.toDto()).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PilotoDTO> update(@PathVariable Integer id, @RequestBody PilotoDTO pilotoDto) {
    	paisService.findById(pilotoDto.getPais().getId());
    	equipeService.findById(pilotoDto.getEquipe().getId());
    	Piloto piloto = new Piloto(pilotoDto);
        piloto.setId(id);
        piloto = service.update(piloto);
        return ResponseEntity.ok(piloto.toDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<List<PilotoDTO>> findByNameIgnoreCase(@PathVariable String name) {
        return ResponseEntity.ok(service.findByNameIgnoreCase(name).stream().map(piloto -> piloto.toDto()).toList());
    }
    
    @GetMapping("/pais/{idPais}")
    public ResponseEntity<List<PilotoDTO>> findByPaisOrderByNameDesc(@PathVariable Integer idPais) {
        return ResponseEntity.ok(service.findByPaisOrderByNameDesc(paisService.findById(idPais)).stream().map(piloto -> piloto.toDto()).toList());
    }
    
    @GetMapping("/equipe/{idEquipe}")
    public ResponseEntity<List<PilotoDTO>> findByEquipeOrderByNameDesc(@PathVariable Integer idEquipe) {
        return ResponseEntity.ok(service.findByEquipeOrderByNameDesc(equipeService.findById(idEquipe)).stream().map(piloto -> piloto.toDto()).toList());
    }

}