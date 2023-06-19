package br.com.trier.springmatutino.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "pista")
public class Pista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pista")
    @Setter
    private Integer id;

    @Column(name = "tamanho_pista")
    private Integer tamanho;

    @Column(name = "id_pais")
    private Integer id_pais;

}