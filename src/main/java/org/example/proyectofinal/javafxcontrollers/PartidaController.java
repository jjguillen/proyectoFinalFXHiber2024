package org.example.proyectofinal.javafxcontrollers;

import javafx.beans.property.ReadOnlyStringWrapper;
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
import lombok.SneakyThrows;
import org.example.proyectofinal.Principal;
import org.example.proyectofinal.entities.Juego;
import org.example.proyectofinal.entities.Jugador;
import org.example.proyectofinal.entities.Partida;
import org.example.proyectofinal.repositories.JuegoRepository;
import org.example.proyectofinal.repositories.PartidaRepository;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PartidaController implements Initializable  {

    @FXML
    public TableView<Partida> tablePartida;

    @FXML
    public TableColumn<Partida, Long> partidaId;

    @FXML
    public TableColumn<Partida, String> partidaFechaHora;

    @FXML
    public TableColumn<Partida, Integer> partidaDuracion;

    @FXML
    public TableColumn<Partida, String> partidaJuego;

    @FXML
    public TableColumn<Partida, String> partidaGanador;



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

    private ComboBox<Partida> comboPartidas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Llamar a Hibernate para traer las partidas
        PartidaRepository pr = new PartidaRepository();
        List<Partida> partidas = pr.findAll();

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm", Locale.forLanguageTag("es-ES"));

        partidaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partidaFechaHora.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getFechaHora().format(formateador)));
        partidaDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        partidaJuego.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getJuego().getNombre()));
        partidaGanador.setCellValueFactory(cellData -> {
            Jugador ganador = cellData.getValue().getGanador();
            return new ReadOnlyStringWrapper((ganador != null) ? ganador.getNombre() : "");
        });

        tablePartida.setItems(FXCollections.observableArrayList(partidas));

        //Modo selección de tabla
        tablePartida.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Menú contextual
        final ContextMenu contextMenu = new ContextMenu();

        //MenuItem delete
        MenuItem itemDelete = new MenuItem("Borrar");
        itemDelete.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Partida partidaABorrar = tablePartida.getSelectionModel().getSelectedItem();
                pr.delete(partidaABorrar);
                partidas.remove(partidaABorrar);
                tablePartida.setItems(FXCollections.observableArrayList(partidas));
                tablePartida.refresh();
            }
        });

        //MenuItem update
        MenuItem itemUpdate = new MenuItem("Modificar");

        //MenuItem añadir jugadores a la partida
        MenuItem itemAddJugador = new MenuItem("Añadir jugador");
        itemAddJugador.setOnAction(new EventHandler<ActionEvent>() {
            @SneakyThrows
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage newWindow = new Stage();
                newWindow.setResizable(false);
                newWindow.initModality(Modality.WINDOW_MODAL);
                newWindow.initOwner(tablePartida.getScene().getWindow());
                newWindow.setTitle("Añadir jugador a la partida");
                FXMLLoader loader = new FXMLLoader(Principal.class.getResource("new-jugador-partida-view.fxml"));
                newWindow.setScene(new Scene(loader.load()));
                newWindow.show();

                //Le paso la partida para que pueda añadir el jugador a esa partida
                Partida partida = tablePartida.getSelectionModel().getSelectedItem();
                AddJugadorPartidaController adjController = loader.getController();
                adjController.setPartida(partida);

            }
        });
        contextMenu.getItems().addAll(itemDelete, itemUpdate,itemAddJugador);
        tablePartida.setContextMenu(contextMenu);

    }

    @FXML
    private void newPartida() throws IOException {
        Stage newWindow = new Stage();
        newWindow.setResizable(false);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(tablePartida.getScene().getWindow());
        newWindow.setTitle("Nuevo Partida");
        FXMLLoader loader = new FXMLLoader(Principal.class.getResource("new-partida-view.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        newWindow.show();

        //Le paso las partidas al nuevo controlador para que actualice la lista
        NuevaPartidaController nuevoController = loader.getController();
        nuevoController.setListaPartidas(tablePartida.getItems());
    }
}
