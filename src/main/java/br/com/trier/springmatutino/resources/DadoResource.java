package br.com.trier.springmatutino.resources;

import java.util.Random;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dados")
public class DadoResource {

	@GetMapping("/jogar")
	public String resultado(
            @RequestParam(name = "qtdDados") Integer qtdDados,
            @RequestParam(name = "aposta") Integer aposta) {

        Random random = new Random();
        int total = 0;
        String jogadas = "";

        if (qtdDados > 0 && qtdDados <= 4) {
            if ((qtdDados*1) <= aposta && (qtdDados*6) >= aposta) {
                for (int i = 1; i <= qtdDados; i++) {
                    int num = random.nextInt(1, 7);
                    jogadas += "\nDado " + i + "º: " + num;
                    total += num;
                }
            } else {
                return "Aposta inválida!";
            }
        } else {
            return "Quantidade da dados inválida!";
        }
        double percent = ((double) total * 100) / (double) aposta;

        return "Quantidade dados: " + qtdDados
                + "\nAposta: " + aposta
                + jogadas
                + "\nTotal: " + total
                + "\nPercentual em relação ao sorteio: " + percent + "%";
    }

}