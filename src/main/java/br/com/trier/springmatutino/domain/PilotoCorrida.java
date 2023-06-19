package br.com.trier.springmatutino.domain;

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

    @Column(name = "id_piloto")
    private Integer id_piloto;

    @Column(name = "id_corrida")
    private Integer id_corrida;

    @Column(name = "colocacao")
    private Integer colocacao;

}