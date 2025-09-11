package peppa.command;

import peppa.task.TaskList;
// import peppa.ui.Ui; (no longer needed)

import java.io.IOException;
import java.util.Scanner;


public class Peppa {
    private TaskList tasks;
    private Parser parser;
    private Storage storage;

    public Peppa(String filePath) {
        this.storage = new Storage(filePath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (IOException e) {
            // handle error, maybe log or set tasks to empty
            this.tasks = new TaskList(new java.util.ArrayList<>());
        }
        this.parser = new Parser(tasks, storage);
    }

    // Removed run() method, not needed for GUI

    // main() not needed for GUI

    /**
     * Generates a response for the user's chat message using Parser and TaskList
     */
    public String getResponse(String input) {
        return parser.parse(input);
    }
}
