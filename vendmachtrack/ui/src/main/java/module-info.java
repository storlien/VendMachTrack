module vendmachtrack.ui {
    // The usage of 'opens' here allows for reflection, required by JavaFX for FXML and for Unit Testing.
    opens vendmachtrack.ui to javafx.fxml, javafx.graphics, org.junit.jupiter, org.mockito, org.testfx;
    opens vendmachtrack.ui.controller to javafx.fxml, javafx.graphics, org.junit.jupiter, org.mockito, org.testfx;


    requires vendmachtrack.core;
    requires vendmachtrack.jsonio;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
}
