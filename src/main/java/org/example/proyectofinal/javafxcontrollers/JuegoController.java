package org.example.proyectofinal.javafxcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import org.example.proyectofinal.Principal;
import org.example.proyectofinal.entities.Juego;
import org.example.proyectofinal.repositories.JuegoRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JuegoController implements Initializable {

    @FXML
    private TableView<Juego> tableJuegos;

    @FXML
    public TableColumn<Juego, Long> juegoId;

    @FXML
    public TableColumn<Juego, String> juegoNombre;

    @FXML
    public TableColumn<Juego, String> juegoPlataforma;

    @FXML
    public TableColumn<Juego, Integer> juegoPegi;

    @FXML
    public TableColumn<Juego, String> juegoCategoria;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Llamar a Hibernate para traer los Juegos
        JuegoRepository jr = new JuegoRepository();
        List<Juego> juegos = jr.findAll();

        //"id" es el nombre del atributo en la clase Juego
        juegoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        juegoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        juegoPlataforma.setCellValueFactory(new PropertyValueFactory<>("plataforma"));
        juegoPegi.setCellValueFactory(new PropertyValueFactory<>("pegi"));
        juegoCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        tableJuegos.setItems(FXCollections.observableArrayList(juegos));

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

    @FXML
    private void newJuego() throws IOException {
        Stage newWindow = new Stage();
        newWindow.setResizable(false);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(tableJuegos.getScene().getWindow());
        newWindow.setTitle("Nuevo Juego");
        FXMLLoader loader = new FXMLLoader(Principal.class.getResource("new-juego-view.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        newWindow.show();
    }

    @FXML
    private void newJugador() throws IOException {
        Stage newWindow = new Stage();
        newWindow.setResizable(false);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(tableJuegos.getScene().getWindow());
        newWindow.setTitle("Nuevo Jugador");
        FXMLLoader loader = new FXMLLoader(Principal.class.getResource("new-jugador-view.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        newWindow.show();
    }

    @FXML
    private void newPartida() throws IOException {

    }
}
