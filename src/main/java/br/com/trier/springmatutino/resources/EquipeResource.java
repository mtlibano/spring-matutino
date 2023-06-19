package br.com.trier.springmatutino.resources;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.services.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/equipe")
public class EquipeResource {

    @Autowired
    EquipeService service;

    @PostMapping
    public ResponseEntity<Equipe> insert(@RequestBody Equipe equipe) {
        Equipe newEquipe = service.salvar(equipe);
        return newEquipe != null ? ResponseEntity.ok(newEquipe) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipe> findById(@PathVariable Integer id) {
        Equipe equipe = service.findById(id);
        return equipe != null ? ResponseEntity.ok(equipe) : ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Equipe>> listAll() {
        List<Equipe> lista = service.listAll();
        return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipe> update(@PathVariable Integer id, @RequestBody Equipe equipe) {
        equipe.setId(id);
        equipe = service.update(equipe);
        return equipe != null ? ResponseEntity.ok(equipe) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Equipe>> findByNameIgnoreCase(@PathVariable String name) {
        List<Equipe> lista = service.findByNameIgnoreCase(name);
        return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
    }

    @GetMapping("/name/contains/{name}")
    public ResponseEntity<List<Equipe>> findByNameContains(@PathVariable String name) {
        List<Equipe> lista = service.findByNameContains(name);
        return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
    }

}