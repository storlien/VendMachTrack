module vendmachtrack.jsonio {
    // Allows the UI application to interact directly with the tracker file.
    exports vendmachtrack.jsonio.internal to vendmachtrack.ui;

    // Weak encapsulation, but we haven't managed to find the unnamed module that is dependent on this module.
    // This will be worked more on in a later release.
    exports vendmachtrack.jsonio;

    requires vendmachtrack.core;
    requires com.google.gson;
}