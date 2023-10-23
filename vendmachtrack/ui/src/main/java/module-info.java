module vendmachtrack.ui {
    opens ui to javafx.graphics, javafx.fxml;

    requires vendmachtrack.core;
    requires vendmachtrack.jsonio;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
}
