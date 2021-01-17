package maze.IO;

public class ConsolePrinter implements IPrint {
    private static ConsolePrinter printer = null;

    private ConsolePrinter() { }

    public static IPrint getInstance() {
        if (printer == null) {
            printer = new ConsolePrinter();
        }
        return printer;
    }

    @Override
    public void print(Object obj) {
        System.out.println(obj);
    }
}
