module vendmachtrack.core {
    requires de.mkammerer.argon2.nolibs;
    requires com.sun.jna; // Requirement necessary for Argon2.

    exports vendmachtrack.core to vendmachtrack.jsonio, vendmachtrack.ui, vendmachtrack.springboot;

    opens vendmachtrack.core to com.google.gson; // This is not desirable. Will be done in a different way in a later release.
}