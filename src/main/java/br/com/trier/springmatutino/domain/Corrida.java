package br.com.trier.springmatutino.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

import br.com.trier.springmatutino.domain.dto.CorridaDTO;
import br.com.trier.springmatutino.utils.DateUtils;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "corrida")
public class Corrida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_corrida")
    @Setter
    private Integer id;

    @Column(name = "data_corrida")
    private ZonedDateTime data;

    @ManyToOne
    private Pista pista;

    @ManyToOne
    private Campeonato campeonato;
    
	//String newDate = this.data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss z"));
    
    public Corrida (CorridaDTO dto, Pista pista, Campeonato campeonato) {
    	this (dto.getId(), 
    			DateUtils.strToZonedDateTime(dto.getData()), 
    			pista,
    			campeonato);
    }
    
    public CorridaDTO toDTO() {
    	return new CorridaDTO(id, DateUtils.zonedDateTimeToStr(data), 
    			pista.getId(), pista.getTamanho(),
    			campeonato.getId(), campeonato.getDescricao());
    }

}