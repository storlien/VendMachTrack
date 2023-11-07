module vendmachtrack.core {
    // Requirements necessary for Argon2 password hashing.
    requires de.mkammerer.argon2.nolibs;
    requires com.sun.jna;

    // Necessary exports to other modules of this project.
    exports vendmachtrack.core to vendmachtrack.jsonio, vendmachtrack.ui, vendmachtrack.springboot;

    // Opening a package is a major decision.
    // After a lot of research and attempts, it seems implementing TypeAdapter is
    // the only way to avoid opening the package to Gson.
    // See readme documentation for more information on this.
    opens vendmachtrack.core to com.google.gson;
}