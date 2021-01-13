package maze.IO;

import java.util.Optional;
import java.util.Scanner;

public class ConsoleReader implements IRead {
    private static final Scanner scanner = new Scanner(System.in);
    private static ConsoleReader reader;

    private ConsoleReader() { }

    public static ConsoleReader getInstance() {
        if (reader == null) {
            reader = new ConsoleReader();
        }

        return reader;
    }

    @Override
    public Optional<String> read() {
        try {
            String input = scanner.nextLine();
            return Optional.of(input);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }
}
