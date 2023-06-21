package br.com.trier.springmatutino.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CampeonatoDTO {
	
	private Integer id;
	private String descricao;
	private Integer ano;

}