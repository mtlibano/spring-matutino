package br.com.trier.springmatutino.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CorridaPaisAnoDTO {
	
	private Integer ano;
	private String pais;
	private List<CorridaDTO> corridas;

}