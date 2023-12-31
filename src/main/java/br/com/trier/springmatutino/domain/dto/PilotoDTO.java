package br.com.trier.springmatutino.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PilotoDTO {
	
	private Integer id;	
	private String name;	
	private Integer idEquipe;
	private String nameEquipe;
	private Integer idPais;
	private String namePais;

}