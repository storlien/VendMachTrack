module vendmachtrack.ui {
    requires vendmachtrack.core;
    requires vendmachtrack.jsonio;
    requires javafx.controls;
    requires javafx.fxml;

    opens ui to javafx.graphics, javafx.fxml;
}
