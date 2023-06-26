package br.com.trier.springmatutino.domain.dto;

import java.util.List;

import br.com.trier.springmatutino.domain.Equipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EquipeCorridaIdDTO {

	private Integer idCorrida;
	private List<Equipe> equipes;

}