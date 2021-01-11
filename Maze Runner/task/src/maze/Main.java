package maze;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int height = scanner.nextInt();
        int width = scanner.nextInt();

        // FIXME for evens numbers
        int rows = height / 2;
        int cols = width / 2;

        Graph mst = new GenerateMaze().generateMaze(rows, cols);
//        IPrint printIt = (mst, rows, cols) -> PrintMaze::print;
//        printIt.print();
        new PrintMaze().print(mst, rows, cols);
    }

}
