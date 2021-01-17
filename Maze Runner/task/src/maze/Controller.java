package maze;

import maze.IO.*;

import java.util.Optional;

public class Controller {
    private Menu menu;
    private IRead reader = ConsoleReader.getInstance();
    private IPrint printer = ConsolePrinter.getInstance();
    private Maze maze;
    private boolean isRunning = true;
    private boolean menuIsNotComplete = true;

    private void buildMenu() {
        menu = new Menu("=== Menu ===")
                .addMenuItem("1", "Generate a new maze", this::generateMaze)
                .addMenuItem("2", "Load a maze", this::loadMaze)
                .addMenuItem("0", "Exit", this::exit);
    }

    private void completeMenu() {
        menu.removeMenuItem("0");

        menu.addMenuItem("3", "Save the maze", this::saveMaze)
                .addMenuItem("4", "Display the maze", this::displayMaze)
                .addMenuItem("0", "Exit", this::exit);

        menuIsNotComplete = false;
    }

    public void execute() {
        buildMenu();

        while (isRunning) {
            if (menuNeedsCompletion()) {
                completeMenu();
            }

            printer.print(menu);
            String actionKey = reader.read().orElse("");
            if (menu.hasAction(actionKey)) {
                menu.runMenuAction(actionKey);
            } else {
                printer.print("Incorrect option. Please try again.");
            }
        }
    }

    private void displayMaze() {
        printer.print(maze);
    }

    private void saveMaze() {
        printer.print("Enter a file name");
        String filename = reader.read().orElse("maze.txt");
        printer = new FilePrinter(filename);
        printer.print(maze.toRawFormat());
        printer = ConsolePrinter.getInstance();
    }

    private boolean menuNeedsCompletion() {
        return maze != null && menuIsNotComplete;
    }

    private void generateMaze() {
        printer.print("Enter the size of a new maze");
        int size = Integer.parseInt(reader.read().orElse("0"));
        if (size > 0) {
            maze = new Maze(size, size);
            displayMaze();
        } else {
            printer.print("Invalid maze size, please try again.");
        }
    }

    private void loadMaze() {
        printer.print("Enter the filename");
        String filename = reader.read().orElse("");
        Optional<String> payload = readFile(filename);

        if (payload.isEmpty()) {
            printer.print("The file " + filename + " does not exist");
            return;
        }

        if (payload.get().isEmpty()) {
            printer.print("Cannot read the file " + filename);
            return;
        }

        maze = getMazeFromString(payload.get());
        if (maze == null) {
            printer.print("Cannot load the maze. It has an invalid format");
        }
    }

    private Maze getMazeFromString(String rawString) {
        try {
            return new Maze(rawString);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private Optional<String> readFile(String filename) {
        reader = new TextFileReader(filename);
        Optional<String> payload = reader.read();
        reader = ConsoleReader.getInstance();
        return payload;
    }

    private void exit() {
        isRunning = false;
    }
}
