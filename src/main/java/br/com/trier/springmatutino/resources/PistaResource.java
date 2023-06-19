package br.com.trier.springmatutino.resources;

import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.services.PistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pista")
public class PistaResource {

    @Autowired
    PistaService service;

    @PostMapping
    public ResponseEntity<Pista> insert(@RequestBody Pista pista) {
        Pista newPista = service.salvar(pista);
        return newPista != null ? ResponseEntity.ok(newPista) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pista> findById(@PathVariable Integer id) {
        Pista pista = service.findById(id);
        return pista != null ? ResponseEntity.ok(pista) : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Pista>> listAll() {
        List<Pista> lista = service.listAll();
        return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pista> update(@PathVariable Integer id, @RequestBody Pista pista) {
        pista.setId(id);
        pista = service.update(pista);
        return pista != null ? ResponseEntity.ok(pista) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}