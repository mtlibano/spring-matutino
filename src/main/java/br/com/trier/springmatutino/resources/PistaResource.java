package br.com.trier.springmatutino.resources;

import br.com.trier.springmatutino.domain.Pista;
import br.com.trier.springmatutino.services.PaisService;
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
    
    @Autowired
    PaisService paisService;

    @PostMapping
    public ResponseEntity<Pista> insert(@RequestBody Pista pista) {
    	paisService.findById(pista.getPais().getId());
        return ResponseEntity.ok(service.salvar(pista));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pista> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Pista>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pista> update(@PathVariable Integer id, @RequestBody Pista pista) {
    	paisService.findById(pista.getPais().getId());
        pista.setId(id);
        return ResponseEntity.ok(service.update(pista));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/tamanho/{tamInicial}/{tamFinal}")
    public ResponseEntity<List<Pista>> findByTamanhoBetween(@PathVariable Integer tamInicial, @PathVariable Integer tamFinal) {
        return ResponseEntity.ok(service.findByTamanhoBetween(tamInicial, tamFinal).stream().map(pista -> pista).toList());
    }
    
    @GetMapping("/pais/{idPais}")
    public ResponseEntity<List<Pista>> findByPais(@PathVariable Integer idPais) {
        return ResponseEntity.ok(service.findByPaisOrderByTamanhoDesc(paisService.findById(idPais)));
    }

}