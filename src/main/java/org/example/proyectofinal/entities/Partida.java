package org.example.proyectofinal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    private Integer duracion;

    @ManyToOne
    private Juego juego;

    @ManyToOne
    private Jugador ganador;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "partidas_jugadores",
            joinColumns = @JoinColumn(name = "partida_id"),
            inverseJoinColumns = @JoinColumn(name = "jugador_id")
    )
    private Set<Jugador> jugadores = new HashSet<>();


    public Partida(LocalDateTime fechaHora, Integer duracion) {
        this.fechaHora = fechaHora;
        this.duracion = duracion;
    }
}
