import java.io.IOException;
import java.util.Scanner;


public class Peppa {
    private static boolean QUIT = false;
    private static Ui ui = new Ui();
    private static TaskList tasks;
    private static Parser parser;

    public static void main(String[] args) {

        Storage storage = new Storage("./data/Peppa.txt");
        try {
            tasks = new TaskList(storage.load(), ui);
        } catch (IOException e) {
            System.out.println(e);
        }
        parser = new Parser(tasks, storage, ui);
        ui.printline();
        ui.printLogo();
        ui.greeting();
        ui.printline();

        Scanner scanner = new Scanner(System.in);
        while (!QUIT) {
            String next = scanner.nextLine();
            QUIT = !parser.parse(next);
        }

        ui.exitMsg();
        ui.printline();
    }
}
