package org.example.proyectofinal.javafxcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Setter;
import org.example.proyectofinal.entities.Juego;
import org.example.proyectofinal.entities.Jugador;
import org.example.proyectofinal.entities.Partida;
import org.example.proyectofinal.repositories.JuegoRepository;
import org.example.proyectofinal.repositories.JugadorRepository;
import org.example.proyectofinal.repositories.PartidaRepository;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class AddJugadorPartidaController {



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

    public Partida partida;

    private List<Jugador> jugadores;

    /**
     * Como initialize, pero pasándole la partida para poder preseleccionar jugadores
     * que están en la partida
     * @param partida
     */
    public void setPartida(Partida partida) {
        this.partida = partida;

        //Esto lo hacía en el initialize, pero no podía acceder a la partida
        //para sacar los jugadores seleccionados, lo he pasado a este método

        JugadorRepository jugadorRepository = new JugadorRepository();
        jugadores = jugadorRepository.findAll();
        jugadorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        jugadorNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        jugadorNick.setCellValueFactory(new PropertyValueFactory<>("nick"));
        jugadorEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        jugadorEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        jugadorIdioma.setCellValueFactory(new PropertyValueFactory<>("idioma"));
        jugadorPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        tableJugadores.setItems(FXCollections.observableArrayList(jugadores));
        tableJugadores.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        //Preseleccionar los jugadores
        for (Jugador jugador : partida.getJugadores()) {
           tableJugadores.getSelectionModel().select(jugadores.stream()
                   .filter(j -> j.getId() == jugador.getId())
                   .findFirst()
                   .orElse(null)
           );
        }
    }

    @FXML
    private void addJugadorPartida() throws IOException {
        //Obtenemos los jugadores seleccionados
        System.out.println("Añadiendo jugador a la partida " + partida.getId());
        Set<Jugador> jugadoresPartida = new HashSet<>(tableJugadores
                .getSelectionModel()
                .getSelectedItems());

        //Cerrar ventana
        Stage stage = (Stage) tableJugadores.getScene().getWindow();
        stage.close();

        //Modificamos la partida con los jugadores nuevos
        partida.setJugadores(jugadoresPartida);
        PartidaRepository pr = new PartidaRepository();
        pr.update(partida);
        pr.closeSession();
    }
}