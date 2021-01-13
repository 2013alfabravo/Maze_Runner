package maze.IO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class TextFileReader implements IRead {
    private final String filename;

    public TextFileReader(String filename) {
        this.filename = filename;
    }

    @Override
    public Optional<String> read() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8))) {
            String input = br.readLine();
            return Optional.of(input);
        } catch (FileNotFoundException e) {
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of("");
        }
    }
}
