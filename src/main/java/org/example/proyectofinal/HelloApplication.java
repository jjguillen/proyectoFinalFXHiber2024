package org.example.proyectofinal;

import jakarta.transaction.Transactional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.proyectofinal.entities.CategoriaJuego;
import org.example.proyectofinal.entities.Juego;
import org.example.proyectofinal.entities.Jugador;
import org.example.proyectofinal.entities.Partida;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @Transactional
    public static void main(String[] args) {

        //HIBERNATE
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        var juego1 = new Juego("Cyberpunk 2077", "PC", 18, CategoriaJuego.SHOOTER);
        session.persist(juego1);

        var jugador1 = new Jugador("Javier Guillén", "Bnei", 46,
                "jj@gmail.com", "español", "España", "");
        session.persist(jugador1);
        jugador1.setJuegoPreferido(juego1);
        session.persist(jugador1);

        var jugador2 = new Jugador("Laura García", "Lauri", 32,
                "la@gmail.com", "español", "España", "");
        session.persist(jugador2);
        jugador2.setJuegoPreferido(juego1);
        session.persist(jugador2);

        var partida1 = new Partida(LocalDateTime.now(), 60);
        session.persist(partida1);
        partida1.setJuego(juego1);
        partida1.setGanador(jugador1);
        partida1.getJugadores().add(jugador1);
        partida1.getJugadores().add(jugador2);
        session.merge(partida1);

        System.out.println("Jugadores de la partida ----------------------------");
        session.find(Partida.class, 1L).getJugadores().forEach(System.out::println);

        session.getTransaction().commit();

        //JAVAFX
        launch();
    }
}