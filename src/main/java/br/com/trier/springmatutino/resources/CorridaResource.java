package br.com.trier.springmatutino.resources;

import br.com.trier.springmatutino.domain.Corrida;
import br.com.trier.springmatutino.services.CorridaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/corrida")
public class CorridaResource {

    @Autowired
    CorridaService service;

    @PostMapping
    public ResponseEntity<Corrida> insert(@RequestBody Corrida corrida) {
        Corrida newCorrida = service.salvar(corrida);
        return newCorrida != null ? ResponseEntity.ok(newCorrida) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Corrida> findById(@PathVariable Integer id) {
        Corrida corrida = service.findById(id);
        return corrida != null ? ResponseEntity.ok(corrida) : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Corrida>> listAll() {
        List<Corrida> lista = service.listtAll();
        return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Corrida> update(@PathVariable Integer id, @RequestBody Corrida corrida) {
        corrida.setId(id);
        corrida = service.update(corrida);
        return corrida != null ? ResponseEntity.ok(corrida) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}