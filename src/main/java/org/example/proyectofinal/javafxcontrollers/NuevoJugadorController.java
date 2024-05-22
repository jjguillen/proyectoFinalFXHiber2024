package org.example.proyectofinal.javafxcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.proyectofinal.entities.CategoriaJuego;
import org.example.proyectofinal.entities.Juego;
import org.example.proyectofinal.entities.Jugador;
import org.example.proyectofinal.repositories.JuegoRepository;
import org.example.proyectofinal.repositories.JugadorRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class NuevoJugadorController implements Initializable {

    @FXML
    public TextField nombre;

    @FXML
    public TextField nick;

    @FXML
    public TextField edad;

    @FXML
    public TextField email;

    @FXML
    public TextField idioma;

    @FXML
    public TextField pais;

    @FXML
    public ChoiceBox<String> juegoPreferido;

    private List<Juego> juegos;

    private ObservableList<Jugador> jugadores;

    public void setListaJugadores(ObservableList<Jugador> players) {
        this.jugadores = players;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JuegoRepository juegoRepository = new JuegoRepository();
        juegos = juegoRepository.findAll();
        juegoRepository.closeSession();

        List<String> juegosString = juegos.stream()
                .map(juego -> juego.getNombre())
                .collect(Collectors.toList());

        ObservableList<String> olJuego = juegoPreferido.getItems();
        olJuego.addAll(FXCollections.observableArrayList(juegosString));
    }

    @FXML
    private void saveJugador() throws IOException {
        //Insertar jugador
        JugadorRepository jugadorRepository = new JugadorRepository();

        Jugador jugador = new Jugador();
        jugador.setNombre(nombre.getText());
        jugador.setNick(nick.getText());
        jugador.setEdad(Integer.parseInt(edad.getText()));
        jugador.setEmail(email.getText());
        jugador.setIdioma(idioma.getText());
        jugador.setPais(pais.getText());

        //Meter el juegoFavorito
        Juego juego = juegos.stream()
                        .filter(j -> j.getNombre().equals(juegoPreferido.getValue()))
                        .findFirst()
                        .orElse(null);
        jugador.setJuegoPreferido(juego);

        //Insertar jugador
        jugadorRepository.insert(jugador);

        //Cerrar sesión
        jugadorRepository.closeSession();

        //Cerrar ventana
        Stage stage = (Stage) nombre.getScene().getWindow();
        stage.close();

        //Actualizamos la lista de juegos
        jugadores.add(jugador);
    }
}