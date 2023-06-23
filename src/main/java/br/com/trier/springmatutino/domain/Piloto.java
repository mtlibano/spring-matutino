package br.com.trier.springmatutino.domain;

import br.com.trier.springmatutino.domain.dto.PilotoDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "piloto")
public class Piloto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_piloto")
    @Setter
    private Integer id;

    @Column(name = "nome_piloto")
    private String name;
    
    @ManyToOne
    private Equipe equipe;

    @ManyToOne
    private Pais pais;
    
    public Piloto(PilotoDTO dto, Equipe equipe, Pais pais) {
    	this(dto.getId(), dto.getName(), equipe, pais);
    }
    
    public PilotoDTO toDTO() {
    	return new PilotoDTO(id, name, equipe.getId(), equipe.getName(), pais.getId(), pais.getName());
    }
    
}