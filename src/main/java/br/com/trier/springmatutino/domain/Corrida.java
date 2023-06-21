package br.com.trier.springmatutino.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

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
    private Date data;

    @ManyToOne
    private Pista pista;

    @ManyToOne
    private Campeonato campeonato;

}