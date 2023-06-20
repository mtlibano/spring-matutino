package br.com.trier.springmatutino.domain;

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
}
