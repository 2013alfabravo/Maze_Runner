package maze.IO;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

public class TextFileReader implements IRead {
    private final String filename;

    public TextFileReader(String filename) {
        this.filename = filename;
    }

    @Override
    public Optional<String> read() {
        try (Scanner scanner = new Scanner(new File(filename), StandardCharsets.UTF_8)) {
            scanner.useDelimiter("\\Z");
            String input = scanner.next();
            return Optional.of(input);
        } catch (FileNotFoundException e) {
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of("");
        }
    }
}
