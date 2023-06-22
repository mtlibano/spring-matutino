package br.com.trier.springmatutino.domain.dto;

import java.time.ZonedDateTime;

import br.com.trier.springmatutino.domain.Campeonato;
import br.com.trier.springmatutino.domain.Pista;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CorridaDTO {
	
	private Integer id;
	private ZonedDateTime data;
	private Pista pista;
	private Campeonato campeonato;

}