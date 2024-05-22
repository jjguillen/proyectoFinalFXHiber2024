package org.example.proyectofinal.javafxcontrollers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.proyectofinal.Principal;
import org.example.proyectofinal.entities.Juego;
import org.example.proyectofinal.entities.Jugador;
import org.example.proyectofinal.repositories.JuegoRepository;
import org.example.proyectofinal.repositories.JugadorRepository;
import org.example.proyectofinal.repositories.PartidaRepository;

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

        //Menú contextual
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem itemDelete = new MenuItem("Borrar");
        itemDelete.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Jugador jugadorABorrar = tableJugadores.getSelectionModel().getSelectedItem();
                jr.delete(jugadorABorrar);
                jugadores.remove(jugadorABorrar);
                tableJugadores.setItems(FXCollections.observableArrayList(jugadores));
                tableJugadores.refresh();
            }
        });
        MenuItem itemEstadisticasJugador = new MenuItem("Estadísticas");
        itemEstadisticasJugador.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //Mostrar Dialog con las partidas que ha jugado y el porcentaje de victorias
                Jugador jugadorEstadisticas = tableJugadores.getSelectionModel().getSelectedItem();
                PartidaRepository pr = new PartidaRepository();

                //Abrir Alert para pintar los datos
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Partidas a las que juega este jugador");
                alert.setHeaderText(null);
                alert.setContentText("El número de partidas es: " +
                        pr.totalPartidasJugadas(jugadorEstadisticas.getId()));

                // Mostrar el diálogo y esperar a que el usuario lo cierre
                alert.showAndWait();
            }
        });

        contextMenu.getItems().addAll(itemDelete, itemEstadisticasJugador);
        tableJugadores.setContextMenu(contextMenu);

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
    private void newJugador() throws IOException {
        Stage newWindow = new Stage();
        newWindow.setResizable(false);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(tableJugadores.getScene().getWindow());
        newWindow.setTitle("Nuevo Jugador");
        FXMLLoader loader = new FXMLLoader(Principal.class.getResource("new-jugador-view.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        newWindow.show();

        //Le paso los jugadores al nuevo controlador para que actualice la lista
        NuevoJugadorController nuevoController = loader.getController();
        nuevoController.setListaJugadores(tableJugadores.getItems());
    }

}
