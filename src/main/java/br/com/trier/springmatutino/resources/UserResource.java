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
import br.com.trier.springmatutino.domain.User;
import br.com.trier.springmatutino.services.UserService;

@RestController
@RequestMapping(value = "/usuarios")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User user) {
		User newUser = service.salvar(user);
		return newUser != null ? ResponseEntity.ok(newUser) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable Integer id) {
		User user = service.findById(id);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.noContent().build(); 
	}
	
	@GetMapping
	public ResponseEntity<List<User>> listAll() {
		List<User> lista = service.listAll();
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build(); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody User user) {
		user.setId(id);
		user = service.update(user);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<User>> findByName(@PathVariable String name) {
		List<User> lista = service.findByName(name);
		return lista.size() > 0 ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build(); 
	}
	
	
	/*List<User> lista = new ArrayList<User>();
	
	public UserResource() {
		lista.add(new User(1, "user1", "user1@email.com", "1234"));
		lista.add(new User(2, "user2", "user2@email.com", "1234"));
		lista.add(new User(3, "user3", "user3@email.com", "1234"));
	}
	
	@GetMapping
	public List<User> listAll() {
		return lista;
	}
	
	/*@GetMapping("/{codigo}")
	public User findById(@PathVariable(name = "codigo") Integer codigo) {
		return lista.stream().filter(user -> user.getId().equals(codigo)).findAny().orElse(null);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<User> findById(@PathVariable(name = "codigo") Integer codigo) {
		User u = lista.stream().filter(user -> user.getId().equals(codigo)).findAny().orElse(null);
		return u != null ? ResponseEntity.ok(u) : ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public User insert(@RequestBody User u) {
		u.setId(lista.size() + 1);
		lista.add(u);
		return u;
	}
	
	
	@GetMapping
	public User findById() {
		User user = new User(1, "Max", "maxtlac@trier.com", "1234");
		return user;
	}*/

}
