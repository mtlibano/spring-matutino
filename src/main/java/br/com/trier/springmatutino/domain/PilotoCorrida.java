package br.com.trier.springmatutino.domain;

import br.com.trier.springmatutino.domain.dto.PilotoCorridaDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "piloto_corrida")
public class PilotoCorrida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter
    private Integer id;
    
    @Column(name = "colocacao")
    private Integer colocacao;

    @ManyToOne
    private Piloto piloto;

    @ManyToOne
    private Corrida corrida;
    
    public PilotoCorrida(PilotoCorridaDTO dto, Piloto piloto, Corrida corrida) {
    	this(dto.getId(), dto.getColocacao(), piloto, corrida);
    }
    
    public PilotoCorridaDTO toDTO() {
		return  new PilotoCorridaDTO(id, colocacao, piloto.getId(), piloto.getName(), corrida.getId());
	}

}