module vendmachtrack.jsonio {
    exports jsonio;

    requires vendmachtrack.core;
    requires com.google.gson;

    opens jsonio to com.google.gson;
}
