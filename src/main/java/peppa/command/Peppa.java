package peppa.command;

import peppa.task.TaskList;
import peppa.ui.Ui;

import java.io.IOException;
import java.util.Scanner;


public class Peppa {
    private boolean QUIT = false;
    private final Ui ui;
    private TaskList tasks;
    private Parser parser;
    private Storage storage;

    public Peppa(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load(), ui);
        } catch (IOException e) {
            System.out.println(e);
        }
        this.parser = new Parser(tasks, storage, ui);
    }

    public void run() {
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

    public static void main(String[] args) {
        new Peppa("./data/Peppa.txt").run();
    }

    /**
     * Generates a reponse for the user's chat message
     */
    public String getResponse(String input) {
        return "Peppa heard: " + input;
    }
}
