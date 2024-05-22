package org.example.proyectofinal.javafxcontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import org.example.proyectofinal.Principal;
import org.example.proyectofinal.entities.Juego;
import org.example.proyectofinal.entities.Jugador;
import org.example.proyectofinal.repositories.JuegoRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.proyectofinal.repositories.PartidaRepository;

public class JuegoController implements Initializable {

    @FXML
    public TableView<Juego> tableJuegos;

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

        juegoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        juegoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        juegoPlataforma.setCellValueFactory(new PropertyValueFactory<>("plataforma"));
        juegoPegi.setCellValueFactory(new PropertyValueFactory<>("pegi"));
        juegoCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        tableJuegos.setItems(FXCollections.observableArrayList(juegos));

        //Modo selección de tabla
        tableJuegos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Menú contextual
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem itemDelete = new MenuItem("Borrar");
        itemDelete.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Juego juegoABorrar = tableJuegos.getSelectionModel().getSelectedItem();
                System.out.println("Juego seleccionado: " + juegoABorrar);
                jr.delete(juegoABorrar);
                juegos.remove(juegoABorrar);
                tableJuegos.setItems(FXCollections.observableArrayList(juegos));
                tableJuegos.refresh();
            }
        });

        MenuItem itemEstadisticasJuego = new MenuItem("Estadísticas");
        itemEstadisticasJuego.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //Mostrar Dialog con las partidas que hay de un juego
                Juego juegoEstadisticas = tableJuegos.getSelectionModel().getSelectedItem();
                PartidaRepository pr = new PartidaRepository();

                //Abrir Alert para pintar los datos
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Partidas que hay de este juego");
                alert.setHeaderText(null);
                alert.setContentText("El número de partidas del juego "
                        + juegoEstadisticas.getNombre() + " es: " +
                        pr.totalPartidasDeUnJuego(juegoEstadisticas.getId()));

                // Mostrar el diálogo y esperar a que el usuario lo cierre
                alert.showAndWait();
            }
        });

        contextMenu.getItems().addAll(itemDelete, itemEstadisticasJuego);
        tableJuegos.setContextMenu(contextMenu);

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

        //Le paso los juegos al nuevo controlador para que actualice la lista
        NuevoJuegoController nuevoController = loader.getController();
        nuevoController.setListaJuegos(tableJuegos.getItems());

    }

}
