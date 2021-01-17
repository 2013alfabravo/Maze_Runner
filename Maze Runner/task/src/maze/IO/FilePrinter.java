package maze.IO;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FilePrinter implements IPrint {
    private final String filename;

    public FilePrinter(String filename) {
        this.filename = filename;
    }

    @Override
    public void print(Object obj) {
        try (FileWriter fw = new FileWriter(filename, StandardCharsets.UTF_8)){
            fw.write(obj.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
