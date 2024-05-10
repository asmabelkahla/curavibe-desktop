module com.example.curavibe_desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires java.sql;


    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.mail;
    requires com.jfoenix;

    opens com.example.curavibe_desktop to javafx.fxml;
    exports com.example.curavibe_desktop;


}