package br.com.trier.springmatutino.resources;

import br.com.trier.springmatutino.domain.Piloto;
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
    public ResponseEntity<Piloto> insert(@RequestBody Piloto piloto) {
    	paisService.findById(piloto.getPais().getId());
    	equipeService.findById(piloto.getEquipe().getId());
        return ResponseEntity.ok(service.salvar(piloto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Piloto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Piloto>> listAll() {
        return ResponseEntity.ok(service.listtAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Piloto> update(@PathVariable Integer id, @RequestBody Piloto piloto) {
    	paisService.findById(piloto.getPais().getId());
    	equipeService.findById(piloto.getEquipe().getId());
        piloto.setId(id);
        return ResponseEntity.ok(service.update(piloto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Piloto>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name).stream().map(piloto -> piloto).toList());
    }
    
    @GetMapping("/pais/{idPais}")
    public ResponseEntity<List<Piloto>> findByPilotoPais(@PathVariable Integer idPais) {
        return ResponseEntity.ok(service.findByPilotoPais(paisService.findById(idPais)));
    }
    
    @GetMapping("/equipe/{idEquipe}")
    public ResponseEntity<List<Piloto>> findByPilotoEquipe(@PathVariable Integer idEquipe) {
        return ResponseEntity.ok(service.findByPilotoEquipe(equipeService.findById(idEquipe)));
    }

}