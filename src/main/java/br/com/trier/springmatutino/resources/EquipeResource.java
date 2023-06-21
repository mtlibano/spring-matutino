package br.com.trier.springmatutino.resources;

import br.com.trier.springmatutino.domain.Equipe;
import br.com.trier.springmatutino.domain.dto.EquipeDTO;
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
    public ResponseEntity<EquipeDTO> insert(@RequestBody Equipe equipe) {
        Equipe newEquipe = service.salvar(equipe);
        return ResponseEntity.ok(newEquipe.toDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipeDTO> findById(@PathVariable Integer id) {
        Equipe equipe = service.findById(id);
        return ResponseEntity.ok(equipe.toDto());
    }

    @GetMapping
    public ResponseEntity<List<EquipeDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(equipe -> equipe.toDto()).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipeDTO> update(@PathVariable Integer id, @RequestBody EquipeDTO equipeDto) {
    	Equipe equipe = new Equipe(equipeDto);
        equipe.setId(id);
        equipe = service.update(equipe);
        return ResponseEntity.ok(equipe.toDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<EquipeDTO>> findByNameIgnoreCase(@PathVariable String name) {
        return ResponseEntity.ok(service.findByNameIgnoreCase(name).stream().map(equipe -> equipe.toDto()).toList());
    }

    @GetMapping("/name/contains/{name}")
    public ResponseEntity<List<EquipeDTO>> findByNameContainsIgnoreCase(@PathVariable String name) {
        return ResponseEntity.ok(service.findByNameContainsIgnoreCase(name).stream().map(equipe -> equipe.toDto()).toList());
    }

}