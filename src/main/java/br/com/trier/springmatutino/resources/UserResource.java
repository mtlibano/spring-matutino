package br.com.trier.springmatutino.resources;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springmatutino.domain.User;

@RestController
@RequestMapping(value = "/usuarios")
public class UserResource {
	
	List<User> lista = new ArrayList<User>();
	
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
	}*/
	
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
	
	/*
	@GetMapping
	public User findById() {
		User user = new User(1, "Max", "maxtlac@trier.com", "1234");
		return user;
	}*/

}
