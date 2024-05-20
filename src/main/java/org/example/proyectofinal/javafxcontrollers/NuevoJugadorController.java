package org.example.proyectofinal.javafxcontrollers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.proyectofinal.entities.CategoriaJuego;
import org.example.proyectofinal.entities.Juego;
import org.example.proyectofinal.repositories.JuegoRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NuevoJugadorController implements Initializable {

    @FXML
    public TextField nombre;

    @FXML
    public TextField nombre;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void saveJugador() throws IOException {
        //Insertar juego
        JuegoRepository juegoRepository = new JuegoRepository();

        Juego juego = new Juego();
        juego.setNombre(nombre.getText());
        juego.setCategoria(categoria.getValue());
        juego.setPlataforma(plataforma.getValue());
        juego.setPegi(pegi.getValue());
        juegoRepository.insert(juego);

        juegoRepository.closeSession();

        //Podríamos recargar el tableview de juegos, sus datos


        //Cerrar ventana
        Stage stage = (Stage) nombre.getScene().getWindow();
        stage.close();
    }
}