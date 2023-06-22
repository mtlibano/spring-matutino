package br.com.trier.springmatutino.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

import br.com.trier.springmatutino.domain.dto.CorridaDTO;

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
    
    public Corrida(CorridaDTO dto) {
    	this(dto.getId(), dto.getData(), dto.getPista(), dto.getCampeonato());
    }
    
    public CorridaDTO toDto() {
    	return new CorridaDTO(this.id, this.data, this.pista, this.campeonato);
    }

}