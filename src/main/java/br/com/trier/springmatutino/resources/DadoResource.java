package br.com.trier.springmatutino.resources;

import java.text.DecimalFormat;
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
            @RequestParam Integer dados,
            @RequestParam Integer aposta) {

        Random random = new Random();
        int total = 0;
        String jogadas = "";

        if (dados > 0 && dados <= 4) {
            if ((dados*1) <= aposta && (dados*6) >= aposta) {
                for (int i = 1; i <= dados; i++) {
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
        double percent;
        if (total > aposta) {
        	percent = ((double) aposta * 100) / (double) total;
        } else {
        	percent = ((double) total * 100) / (double) aposta;
        }        

        return "Quantidade dados: " + dados
                + "\nAposta: " + aposta
                + jogadas
                + "\nTotal: " + total
                + "\nPercentual aos valores: " + new DecimalFormat(".##").format(percent) + "%";
    }

}