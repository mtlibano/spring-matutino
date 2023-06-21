package br.com.trier.springmatutino.resources;

import java.util.List;
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
import br.com.trier.springmatutino.domain.Pais;
import br.com.trier.springmatutino.domain.dto.PaisDTO;
import br.com.trier.springmatutino.services.PaisService;

@RestController
@RequestMapping(value = "/pais")
public class PaisResource {
	
	@Autowired
	private PaisService service;
	
	@PostMapping
	public ResponseEntity<PaisDTO> insert(@RequestBody PaisDTO pais) {
		Pais newPais = service.salvar(new Pais(pais));
		return ResponseEntity.ok(newPais.toDto());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PaisDTO> findById(@PathVariable Integer id) {
		Pais pais = service.findById(id);
		return ResponseEntity.ok(pais.toDto()); 
	}
	
	@GetMapping
	public ResponseEntity<List<PaisDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map(pais -> pais.toDto()).toList()); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PaisDTO> update(@PathVariable Integer id, @RequestBody PaisDTO paisDto) {
		Pais pais = new Pais(paisDto);
		pais.setId(id);
		pais = service.update(pais);
		return ResponseEntity.ok(pais.toDto());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<PaisDTO>> findByNameIgnoreCase(@PathVariable String name) {
		return ResponseEntity.ok(service.findByNameIgnoreCase(name).stream().map(pais -> pais.toDto()).toList()); 
	}

	@GetMapping("/name/contains/{name}")
	public ResponseEntity<List<PaisDTO>> findByNameContainsIgnoreCase(@PathVariable String name) {
		return ResponseEntity.ok(service.findByNameContainsIgnoreCase(name).stream().map(pais -> pais.toDto()).toList());
	}

}