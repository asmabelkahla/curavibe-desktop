module com.example.curavibe_desktop {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.curavibe_desktop to javafx.fxml;
    exports com.example.curavibe_desktop;
}