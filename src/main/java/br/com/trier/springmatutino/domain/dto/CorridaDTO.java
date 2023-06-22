package br.com.trier.springmatutino.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CorridaDTO {
	
	private Integer id;
	private String data;
	private Integer pistaId;
	private Integer pistaTamanho;
	private Integer campeonatoId;
	private String campeonatoDescricao;

}