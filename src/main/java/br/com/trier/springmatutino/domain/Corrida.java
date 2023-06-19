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

    @Column(name = "id_pista")
    private Integer id_pista;

    @Column(name = "id_camp")
    private Integer id_camp;

}