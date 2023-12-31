package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    /**
     * The main method serves as the starting point for the execution of the Java
     * application. While it is common practice to have the main method in a class
     * named Main, the choice of having it in a single file (Main.java) is not a
     * strict requirement. It's more of a convention that many developers follow for
     * clarity and organization.
     */
    public static void main(final String... args) throws IOException {
        String filename = Files.readString(Paths.get("config.txt")).strip();

        new WebServer(filename);
    }

}
