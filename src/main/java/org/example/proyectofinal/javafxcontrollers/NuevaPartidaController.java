package org.example.proyectofinal.javafxcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.proyectofinal.entities.Juego;
import org.example.proyectofinal.entities.Jugador;
import org.example.proyectofinal.entities.Partida;
import org.example.proyectofinal.repositories.JuegoRepository;
import org.example.proyectofinal.repositories.JugadorRepository;
import org.example.proyectofinal.repositories.PartidaRepository;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class NuevaPartidaController implements Initializable {

    @FXML
    public DatePicker fecha;

    @FXML
    public TextField duracion;

    @FXML
    public ChoiceBox<String> juego;

    @FXML
    public ChoiceBox<String> ganador;


    private List<Juego> juegos;

    private List<Jugador> jugadores;

    private ObservableList<Partida> partidas;

    public void setListaPartidas(ObservableList<Partida> matches) {
        this.partidas = matches;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JuegoRepository juegoRepository = new JuegoRepository();
        juegos = juegoRepository.findAll();
        juegoRepository.closeSession();
        List<String> juegosString = juegos.stream()
                .map(juego -> juego.getNombre())
                .collect(Collectors.toList());
        ObservableList<String> olJuego = juego.getItems();
        olJuego.addAll(FXCollections.observableArrayList(juegosString));

        JugadorRepository jugadorRepository = new JugadorRepository();
        jugadores = jugadorRepository.findAll();
        List<String> jugadoresString = jugadores.stream()
                .map(jugador -> jugador.getNombre())
                .collect(Collectors.toList());
        ObservableList<String> olJugador = ganador.getItems();
        olJugador.addAll(FXCollections.observableArrayList(jugadoresString));


    }

    @FXML
    private void savePartida() throws IOException {
        //Insertar partida
        PartidaRepository partidaRepository = new PartidaRepository();

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm", Locale.forLanguageTag("es-ES"));

        Partida partida = new Partida();
        LocalDate fechaSola = fecha.getValue();
        partida.setFechaHora(fechaSola.atStartOfDay());
        partida.setDuracion(Integer.parseInt(duracion.getText()));

        //Meter el juego
        Juego juegoPartida = juegos.stream()
                .filter(j -> j.getNombre().equals(juego.getValue()))
                .findFirst()
                .orElse(null);
        partida.setJuego(juegoPartida);

        //Insertar ganador
        Jugador ganadorPartida = jugadores.stream()
                .filter(j -> j.getNombre().equals(ganador.getValue()))
                .findFirst()
                .orElse(null);
        partida.setGanador(ganadorPartida);

        partidaRepository.insert(partida);

        //Cerrar sesión
        partidaRepository.closeSession();

        //Cerrar ventana
        Stage stage = (Stage) fecha.getScene().getWindow();
        stage.close();

        //Actualizamos la lista de partidas
        partidas.add(partida);
    }
}