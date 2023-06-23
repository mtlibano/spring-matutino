package br.com.trier.springmatutino.domain.dto;

import java.util.List;

import br.com.trier.springmatutino.domain.Pista;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PistaCampeonatoIdDTO {
	
	private Integer idCampeonato;
	private String descricaoCampeonato;
	private List<Pista> pistas;

}