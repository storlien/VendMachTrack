package vendmachtrack.ui;

/**
 * Class to call main method of JavaFX application
 * in order to solve dependency problems when creating shaded uber-jar.
 */
public final class Main {

    /**
     * Private constructor to prevent instantiation of class.
     */
    private Main() {
    }

    public static void main(final String[] args) {
        App.main(args);
    }
}
