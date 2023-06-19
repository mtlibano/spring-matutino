package br.com.trier.springmatutino.resources;

import br.com.trier.springmatutino.domain.Piloto;
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

    @PostMapping
    public ResponseEntity<Piloto> insert(@RequestBody Piloto piloto) {
        Piloto newPiloto = service.salvar(piloto);
        return newPiloto != null ? ResponseEntity.ok(newPiloto) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Piloto> findById(@PathVariable Integer id) {
        Piloto piloto = service.findById(id);
        return piloto != null ? ResponseEntity.ok(piloto) : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Piloto>> listAll() {
        List<Piloto> lista = service.listtAll();
        return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Piloto> update(@PathVariable Integer id, @RequestBody Piloto piloto) {
        piloto.setId(id);
        piloto = service.update(piloto);
        return piloto != null ? ResponseEntity.ok(piloto) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}