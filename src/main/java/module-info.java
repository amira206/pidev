module com.example.gestionevenement3a16 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.jsobject;
    requires java.desktop;
    requires javafx.graphics;
    requires com.google.gson;
    requires unirest.java;
    requires twilio;
    requires java.mail;

    opens GestionEvenement3a16.Entity to com.google.gson;

    opens GestionEvenement3a16 to javafx.fxml;
    exports GestionEvenement3a16;
    exports connectionSql;
    opens connectionSql to javafx.fxml;
    exports GestionEvenement3a16.Controller;

    exports GestionEvenement3a16.Controller.ReclamationController;
    opens GestionEvenement3a16.Controller.ReclamationController to javafx.fxml;
    opens GestionEvenement3a16.Controller to javafx.fxml;
    exports GestionEvenement3a16.Entity;
  //  exports GestionEvenement3a16.Services;
    opens GestionEvenement3a16.Services to com.google.gson;
}

