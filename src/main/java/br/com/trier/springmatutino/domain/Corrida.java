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
    
    public CorridaDTO toDTO() {
		return  new CorridaDTO(id, DateUtils.zonedDateTimeToStr(data), pista.getId(), pista.getTamanho(), campeonato.getId(), campeonato.getDescricao());
	}
	
	public Corrida(CorridaDTO dto) {
		 this(dto.getId(),
				 DateUtils.strToZonedDateTime(dto.getData()),
				 new Pista(dto.getPistaId(), null, null),
				 new Campeonato(dto.getCampeonatoId(), null, null));
	}
	
	public Corrida(CorridaDTO dto, Pista pista, Campeonato campeonato) {
		 this(dto.getId(),
				 DateUtils.strToZonedDateTime(dto.getData()),
				 pista,
				 campeonato);
	}
	
}