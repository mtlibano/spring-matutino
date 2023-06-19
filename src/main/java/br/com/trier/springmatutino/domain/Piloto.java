package br.com.trier.springmatutino.domain;

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

    @Column(name = "id_equipe")
    private Integer id_equipe;

    @Column(name = "id_pais")
    private Integer id_pais;
}
