package br.com.trier.springmatutino.domain;

import br.com.trier.springmatutino.domain.dto.CampeonatoDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "campeonato")
public class Campeonato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_camp")
    @Setter
    private Integer id;

    @Column(name = "descricao_camp")
    private String descricao;

    @Column(name = "ano_camp")
    private Integer ano;
    
    public Campeonato(CampeonatoDTO dto) {
    	this(dto.getId(), dto.getDescricao(), dto.getAno());
    }
    
    public CampeonatoDTO toDto() {
    	return new CampeonatoDTO(this.id, this.descricao, this.ano);
    }
    
}