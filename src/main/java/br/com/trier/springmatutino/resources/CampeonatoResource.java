package br.com.trier.springmatutino.resources;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.services.CampeonatoService;
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
@RequestMapping(value = "/campeonato")
public class CampeonatoResource {

    @Autowired
    CampeonatoService service;

    @PostMapping
    public ResponseEntity<Campeonato> insert(@RequestBody Campeonato campeonato) {
        Campeonato newCampeonato = service.salvar(campeonato);
        return newCampeonato != null ? ResponseEntity.ok(newCampeonato) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campeonato> findById(@PathVariable Integer id) {
        Campeonato campeonato = service.findById(id);
        return campeonato != null ? ResponseEntity.ok(campeonato) : ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Campeonato>> listAll() {
        List<Campeonato> lista = service.listAll();
        return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campeonato> update(@PathVariable Integer id, @RequestBody Campeonato campeonato) {
        campeonato.setId(id);
        campeonato = service.update(campeonato);
        return campeonato != null ? ResponseEntity.ok(campeonato) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Campeonato>> findByDescricaoIgnoreCase(@PathVariable String descricao) {
        List<Campeonato> lista = service.findByDescricaoIgnoreCase(descricao);
        return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
    }

    @GetMapping("/descricao/contains/{descricao}")
    public ResponseEntity<List<Campeonato>> findByDescricaoContainsIgnoreCase(@PathVariable String descricao) {
        List<Campeonato> lista = service.findByDescricaoContainsIgnoreCase(descricao);
        return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
    }

    @GetMapping("/ano/{ano}")
    public ResponseEntity<List<Campeonato>> findByAno(@PathVariable Integer ano) {
        List<Campeonato> lista = service.findByAno(ano);
        return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
    }

    @GetMapping("/ano/{ano1}/{ano2}")
    public ResponseEntity<List<Campeonato>> findByAno(@PathVariable Integer ano1, @PathVariable Integer ano2) {
        List<Campeonato> lista = service.findByAnoBetween(ano1, ano2);
        return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
    }
    
    @GetMapping("/descricao-ano/{descricao}/{ano}")
    public ResponseEntity<List<Campeonato>> findByDescricaoIgnoreCaseAndAnoEquals(@PathVariable String descricao, @PathVariable Integer ano) {
        List<Campeonato> lista = service.findByDescricaoIgnoreCaseAndAnoEquals(descricao, ano);
        return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
    }

}