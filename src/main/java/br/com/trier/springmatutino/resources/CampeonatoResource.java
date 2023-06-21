package br.com.trier.springmatutino.resources;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.dto.CampeonatoDTO;
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
    public ResponseEntity<CampeonatoDTO> insert(@RequestBody CampeonatoDTO camp) {
        Campeonato newCampeonato = service.salvar(new Campeonato(camp));
        return ResponseEntity.ok(newCampeonato.toDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampeonatoDTO> findById(@PathVariable Integer id) {
        Campeonato campeonato = service.findById(id);
        return ResponseEntity.ok(campeonato.toDto());
    }

    @GetMapping
    public ResponseEntity<List<CampeonatoDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(campeonato -> campeonato.toDto()).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampeonatoDTO> update(@PathVariable Integer id, @RequestBody CampeonatoDTO camp) {
    	Campeonato campeonato = new Campeonato(camp);
        campeonato.setId(id);
        campeonato = service.update(campeonato);
        return ResponseEntity.ok(campeonato.toDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<CampeonatoDTO>> findByDescricaoIgnoreCase(@PathVariable String descricao) {
        return ResponseEntity.ok(service.findByDescricaoIgnoreCase(descricao).stream().map(campeonato -> campeonato.toDto()).toList());
    }

    @GetMapping("/descricao/contains/{descricao}")
    public ResponseEntity<List<CampeonatoDTO>> findByDescricaoContainsIgnoreCase(@PathVariable String descricao) {
        return ResponseEntity.ok(service.findByDescricaoContainsIgnoreCase(descricao).stream().map(campeonato -> campeonato.toDto()).toList());
    }

    @GetMapping("/ano/{ano}")
    public ResponseEntity<List<CampeonatoDTO>> findByAno(@PathVariable Integer ano) {
        return ResponseEntity.ok(service.findByAno(ano).stream().map(campeonato -> campeonato.toDto()).toList());
    }

    @GetMapping("/ano/{ano1}/{ano2}")
    public ResponseEntity<List<CampeonatoDTO>> findByAnoBetween(@PathVariable Integer ano1, @PathVariable Integer ano2) {
        return ResponseEntity.ok(service.findByAnoBetween(ano1, ano2).stream().map(campeonato -> campeonato.toDto()).toList());
    }
    
    @GetMapping("/descricao-ano/{descricao}/{ano}")
    public ResponseEntity<List<CampeonatoDTO>> findByDescricaoIgnoreCaseAndAnoEquals(@PathVariable String descricao, @PathVariable Integer ano) {
        return ResponseEntity.ok(service.findByDescricaoIgnoreCaseAndAnoEquals(descricao, ano).stream().map(campeonato -> campeonato.toDto()).toList());
    }

}