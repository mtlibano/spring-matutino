package br.com.trier.springmatutino.resources;

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.services.CampeonatoService;
import br.com.trier.springmatutino.services.CorridaService;
import br.com.trier.springmatutino.services.PistaService;

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
    public ResponseEntity<Corrida> insert(@RequestBody Corrida corrida) {
    	pistaService.findById(corrida.getPista().getId());
    	campeonatoService.findById(corrida.getCampeonato().getId());
        return ResponseEntity.ok(service.salvar(corrida));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Corrida> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Corrida>> listAll() {
        return ResponseEntity.ok(service.listtAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Corrida> update(@PathVariable Integer id, @RequestBody Corrida corrida) {
    	pistaService.findById(corrida.getPista().getId());
    	campeonatoService.findById(corrida.getCampeonato().getId());
        corrida.setId(id);
        return ResponseEntity.ok(service.update(corrida));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/pista/{idPista}")
    public ResponseEntity<List<Corrida>> findByCorridaPista(@PathVariable Integer idPista) {
        return ResponseEntity.ok(service.findByCorridaPista(pistaService.findById(idPista)));
    }
    
    @GetMapping("/campeonato/{idCampeonato}")
    public ResponseEntity<List<Corrida>> findByCorridaCampeonato(@PathVariable Integer idCampeonato) {
        return ResponseEntity.ok(service.findByCorridaCampeonato(campeonatoService.findById(idCampeonato)));
    }

}