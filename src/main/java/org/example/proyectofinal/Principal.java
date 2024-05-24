package org.example.proyectofinal;

import jakarta.transaction.Transactional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import java.io.IOException;

public class Principal extends Application {
    private static Scene scene;

    @Override
    public void stop() throws Exception {
        //¿Cerrar sesiones de Hibernate?
        System.out.println("Bye!!");
        super.stop();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("juegos-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 1024, 650);
        stage.setResizable(false);
        stage.setTitle("PARTIDAS DE VIDEOJUEGOS");
        stage.setScene(scene);
        stage.show();
    }

    @Transactional
    public static void main(String[] args) {

        //JAVAFX
        launch();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}