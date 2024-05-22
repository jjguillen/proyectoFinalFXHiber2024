package org.example.proyectofinal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String nick;

    private Integer edad;

    private String email;

    private String idioma;

    private String pais;

    private String avatar;

    @ManyToOne
    @JoinColumn(name = "juego_preferido")
    private Juego juegoPreferido;


    public Jugador(String nombre, String nick, Integer edad, String email, String idioma, String pais, String avatar) {
        this.nombre = nombre;
        this.nick = nick;
        this.edad = edad;
        this.email = email;
        this.idioma = idioma;
        this.pais = pais;
        this.avatar = avatar;
    }


}
