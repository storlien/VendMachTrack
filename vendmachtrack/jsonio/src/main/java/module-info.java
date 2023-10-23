module vendmachtrack.jsonio {
    exports jsonio.internal to vendmachtrack.ui;
    exports jsonio;

    requires vendmachtrack.core;
    requires com.google.gson;
}