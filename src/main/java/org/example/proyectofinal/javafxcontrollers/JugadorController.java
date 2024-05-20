package org.example.proyectofinal.javafxcontrollers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.proyectofinal.Principal;
import org.example.proyectofinal.entities.Juego;
import org.example.proyectofinal.entities.Jugador;
import org.example.proyectofinal.repositories.JuegoRepository;
import org.example.proyectofinal.repositories.JugadorRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class JugadorController implements Initializable  {

    @FXML
    private TableView<Jugador> tableJugadores;

    @FXML
    public TableColumn<Jugador, Long> jugadorId;

    @FXML
    public TableColumn<Jugador, String> jugadorNombre;

    @FXML
    public TableColumn<Jugador, String> jugadorNick;

    @FXML
    public TableColumn<Jugador, Integer> jugadorEdad;

    @FXML
    public TableColumn<Jugador, String> jugadorEmail;

    @FXML
    public TableColumn<Jugador, String> jugadorIdioma;

    @FXML
    public TableColumn<Jugador, String> jugadorPais;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Llamar a Hibernate para traer los Jugadores
        JugadorRepository jr = new JugadorRepository();
        List<Jugador> jugadores = jr.findAll();

        jugadorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        jugadorNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        jugadorNick.setCellValueFactory(new PropertyValueFactory<>("nick"));
        jugadorEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        jugadorEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        jugadorIdioma.setCellValueFactory(new PropertyValueFactory<>("idioma"));
        jugadorPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        tableJugadores.setItems(FXCollections.observableArrayList(jugadores));

    }

    @FXML
    private void toJuegos() throws IOException {
        Principal.setRoot("juegos-view");
    }

    @FXML
    private void toJugadores() throws IOException {
        Principal.setRoot("jugadores-view");
    }

    @FXML
    private void toPartidas() throws IOException {
        Principal.setRoot("partidas-view");
    }

}
