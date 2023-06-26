package br.com.trier.springmatutino.resources;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.trier.springmatutino.domain.User;
import br.com.trier.springmatutino.domain.dto.UserDTO;
import br.com.trier.springmatutino.services.UserService;

@RestController
@RequestMapping(value = "/usuarios")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserDTO user) {
		User newUser = service.salvar(new User(user));
		return newUser != null ? ResponseEntity.ok(newUser.toDto()) : ResponseEntity.badRequest().build();
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
		User user = service.findById(id);
		return ResponseEntity.ok(user.toDto());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<UserDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map(user -> user.toDto()).toList());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
		User user = new User(userDTO);
		user.setId(id);
		user = service.update(user);
		return user != null ? ResponseEntity.ok(user.toDto()) : ResponseEntity.badRequest().build();
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/name-list/{name}")
	public ResponseEntity<List<UserDTO>> findByNameIgnoreCase(@PathVariable String name) {
		return ResponseEntity.ok(service.findByNameIgnoreCase(name).stream().map(user -> user.toDto()).toList());
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
