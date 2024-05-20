module org.example.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires jakarta.transaction;
    requires lombok;

    opens org.example.proyectofinal to javafx.fxml;
    exports org.example.proyectofinal;
    opens org.example.proyectofinal.entities;
    opens org.example.proyectofinal.repositories;
    exports org.example.proyectofinal.javafxcontrollers;
    opens org.example.proyectofinal.javafxcontrollers to javafx.fxml;
}