package maze;

import maze.IO.*;
import java.util.Optional;

public class Controller {
    private Menu menu;
    private IRead reader = ConsoleReader.getInstance();
    private IPrint printer = ConsolePrinter.getInstance();
    private Maze maze;
    private boolean isRunning = true;
    private boolean menuHasInvisibleItems = true;

    private void buildMenu() {
        menu = new Menu()
                .setTitle("=== Menu ===")
                .addMenuItem("1", "Generate a new maze", this::generateMaze)
                .addMenuItem("2", "Load a maze", this::loadMaze)
                .addMenuItem("3", "Save the maze", this::saveMaze)
                .addMenuItem("4", "Display the maze", this::displayMaze)
                .addMenuItem("5", "Find the escape", this::findEscape)
                .addMenuItem("0", "Exit", this::exit)
                .setInvisible("3")
                .setInvisible("4")
                .setInvisible("5");
    }

    private void makeAllMenuItemsVisible() {
        menu.setVisible("3")
        .setVisible("4")
        .setVisible("5");

        menuHasInvisibleItems = false;
    }

    public void execute() {
        buildMenu();

        while (isRunning) {
            if (menuNeedsUpdating()) {
                makeAllMenuItemsVisible();
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
        printer.print(maze.toString());
        printer = ConsolePrinter.getInstance();
    }

    private boolean menuNeedsUpdating() {
        return maze != null && menuHasInvisibleItems;
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

        Maze maze = Maze.fromString(payload.get());
        if (maze == null) {
            printer.print("Cannot load the maze. It has an invalid format");
        } else {
            this.maze = maze;
        }
    }

    private void findEscape() {
        printer.print(new MazeSolver(maze).getMazeSolution());
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
