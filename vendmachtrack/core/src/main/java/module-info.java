module vendmachtrack.core {
    requires de.mkammerer.argon2.nolibs;
    requires com.sun.jna; // Requirement necesasry for Argon2.

    exports core to vendmachtrack.jsonio, vendmachtrack.ui;

    opens core to com.google.gson; // This is not desirable. Will be done in a different way in a later release.
}