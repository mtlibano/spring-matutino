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
    
    @ManyToOne
    private Equipe equipe;

    @ManyToOne
    private Pais pais;
    
}