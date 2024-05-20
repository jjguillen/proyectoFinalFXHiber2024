package org.example.proyectofinal.javafxcontrollers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.proyectofinal.Principal;
import org.example.proyectofinal.entities.CategoriaJuego;
import org.example.proyectofinal.entities.Juego;
import org.example.proyectofinal.repositories.JuegoRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NuevoJuegoController implements Initializable {

    @FXML
    public TextField nombre;

    @FXML
    public ChoiceBox<CategoriaJuego> categoria;

    @FXML
    public ChoiceBox<String> plataforma;

    @FXML
    public ChoiceBox<Integer> pegi;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<CategoriaJuego> olCategoria = categoria.getItems();
        olCategoria.addAll(CategoriaJuego.DEPORTIVO, CategoriaJuego.CONDUCCION,
                CategoriaJuego.ESTRATEGIA, CategoriaJuego.MMO, CategoriaJuego.MMO,
                CategoriaJuego.SHOOTER);
        categoria.setValue(CategoriaJuego.DEPORTIVO);

        ObservableList<String> olPlataforma = plataforma.getItems();
        olPlataforma.addAll("PC", "XBOX 360", "PS5", "Nintendo Switch");
        plataforma.setValue("PC");

        ObservableList<Integer> olPegi = pegi.getItems();
        olPegi.addAll(3,7,12,16,18);
        pegi.setValue(7);
    }

    @FXML
    private void saveJuego() throws IOException {
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