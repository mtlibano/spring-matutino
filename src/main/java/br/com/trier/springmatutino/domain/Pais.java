package br.com.trier.springmatutino.domain;

import br.com.trier.springmatutino.domain.dto.PaisDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "pais")
public class Pais {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pais")
	@Setter
	private Integer id;
	
	@Column(name = "nome_pais", unique = true)
	private String name;
	
	public Pais(PaisDTO dto) {
		this(dto.getId(), dto.getName());
	}
	
	public PaisDTO toDto() {
		return new PaisDTO(this.id, this.name);
	}

}