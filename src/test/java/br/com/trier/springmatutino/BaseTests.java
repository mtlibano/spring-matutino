package br.com.trier.springmatutino;

import br.com.trier.springmatutino.services.*;
import br.com.trier.springmatutino.services.impl.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests {
	
	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}

	@Bean
	public PaisService paisService() {
		return new PaisServiceImpl();
	}

	@Bean
	public EquipeService equipeService() {
		return new EquipeServiceImpl();
	}

	@Bean
	public CampeonatoService campeonatoService() {
		return new CampeonatoServiceImpl();
	}

	@Bean
	public PistaService pistaService() {
		return new PistaServiceImpl();
	}

}
