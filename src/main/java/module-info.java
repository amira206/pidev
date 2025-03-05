module org.example.pitest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jdk.jsobject;
    requires java.desktop;
    requires javafx.graphics;
    requires com.google.gson;
    requires unirest.java;
    requires twilio;
    requires java.management;
    opens GestionEvenement3a16.Entity to com.google.gson;

    opens org.example.pitest to javafx.fxml;
    opens org.example.pitest.Controllers to javafx.fxml; // ✅ Ajouté pour permettre l'accès via FXMLLoader

    exports org.example.pitest;
    exports org.example.pitest.Controllers; // ✅ Si vous voulez que d'autres modules accèdent au package
}
