package br.com.trier.springmatutino.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/calc")
public class MathResource {
	
	@GetMapping("/somar")
	public Integer somar(@RequestParam(name = "n1")Integer n1, @RequestParam(name = "n2") Integer n2) {
		return n1 + n2;
	}
	
	@GetMapping("/subtrair")
	public Integer subtrair(@RequestParam(name = "n1")Integer n1, @RequestParam(name = "n2") Integer n2) {
		return n1 - n2;
	}
	
	@GetMapping("/multi")
	public Integer multi(@RequestParam(name = "n1")Integer n1, @RequestParam(name = "n2") Integer n2) {
		return n1 * n2;
	}
	
	@GetMapping("/div")
	public Integer div(@RequestParam(name = "n1")Integer n1, @RequestParam(name = "n2") Integer n2) {
		return n1 / n2;
	}
	
	@GetMapping("/somar/{n1}/{n2}")
	public Integer somarJson(@PathVariable(name = "n1")Integer n1, @PathVariable(name = "n2") Integer n2) {
		return n1 + n2;
	}

}