package br.com.trier.springmatutino.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PilotoCorridaDTO {
	
	private Integer id;
	private Integer colocacao;
	private Integer idPiloto;
	private String namePiloto;
	private Integer idCorrida;

}