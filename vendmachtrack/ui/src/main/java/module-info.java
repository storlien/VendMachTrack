module vendmachtrack.ui {
    // The usage of 'opens' here allows for reflection, required by JavaFX for FXML.
    opens vendmachtrack.ui to javafx.graphics, javafx.fxml;

    requires vendmachtrack.core;
    requires vendmachtrack.jsonio;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
}
