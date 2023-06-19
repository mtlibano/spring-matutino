package br.com.trier.springmatutino.resources;

import br.com.trier.springmatutino.domain.PilotoCorrida;
import br.com.trier.springmatutino.services.PilotoCorridaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/piloto_corrida")
public class PilotoCorridaResource {

    @Autowired
    PilotoCorridaService service;

    @PostMapping
    public ResponseEntity<PilotoCorrida> insert(@RequestBody PilotoCorrida pilotoCorrida) {
        PilotoCorrida newPilotoCorrida = service.salvar(pilotoCorrida);
        return newPilotoCorrida != null ? ResponseEntity.ok(newPilotoCorrida) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PilotoCorrida> findById(@PathVariable Integer id) {
        PilotoCorrida pilotoCorrida = service.findById(id);
        return pilotoCorrida != null ? ResponseEntity.ok(pilotoCorrida) : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<PilotoCorrida>> listAll() {
        List<PilotoCorrida> lista = service.listtAll();
        return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PilotoCorrida> update(@PathVariable Integer id, @RequestBody PilotoCorrida pilotoCorrida) {
        pilotoCorrida.setId(id);
        pilotoCorrida = service.update(pilotoCorrida);
        return pilotoCorrida != null ? ResponseEntity.ok(pilotoCorrida) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}