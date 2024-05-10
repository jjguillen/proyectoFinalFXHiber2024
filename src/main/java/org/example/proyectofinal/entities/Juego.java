package org.example.proyectofinal.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String plataforma;

    private Integer pegi;

    @Enumerated
    private CategoriaJuego categoria;

    public Juego(String nombre, String plataforma, Integer pegi, CategoriaJuego categoria) {
        this.nombre = nombre;
        this.plataforma = plataforma;
        this.pegi = pegi;
        this.categoria = categoria;
    }
}
