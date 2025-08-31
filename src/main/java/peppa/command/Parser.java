package peppa.command;

import peppa.task.TaskList;
import peppa.ui.Ui;

/**
 * Parses a raw user command and triggers the matching TaskList / Storage / Ui action.
 */
public class Parser {
    private final TaskList tasks;
    private final Storage storage;
    private final Ui ui;

    /**
     * Creates a parser bound to the given collaborators.
     *
     * @param tasks    mutable task catalogue to operate on
     * @param storage  persistence layer used for serialising {@code tasks}
     * @param ui       user-interface helper for printing separators / prompts
     */
    public Parser(TaskList tasks, Storage storage, Ui ui) {
        this.tasks = tasks;
        this.storage = storage;
        this.ui = ui;
    }

    /**
     * Analyse a single command line and execute the matching action.
     *
     * @param input raw command string typed by the user
     * @return {@code false} if the user typed {@literal "bye"} (caller should terminate);
     *         {@code true} otherwise
     */
    public boolean parse(String input) {
        input = input.trim();
        String command = input.split("\\s+", 2)[0];   //takes the first word
        switch (command) {
        case "bye":
            return false; //it will end the loop
        case "list":
            tasks.displayTasks();
            break;
        case "unmark": {
            String[] parts = input.split(" ");
            tasks.unmarkTask(Integer.parseInt(parts[1]) - 1);
            storage.save(tasks);
            break;
        }
        case "mark": {
            String[] parts = input.split(" ");
            tasks.markTask(Integer.parseInt(parts[1]) - 1);
            storage.save(tasks);
            break;
        }
        case "delete": {
            String[] parts = input.split(" ");
            tasks.deleteTask(Integer.parseInt(parts[1]) - 1);
            storage.save(tasks);
            break;
        }
        case "find": {
            String toFind = input.split("\\s+", 2)[1];
            tasks.findTask(toFind);
            break;
        }
        case "todo":
        case "deadline":
        case "event":
            tasks.addTask(input);
            storage.save(tasks);
            break;

        default:
            System.out.println("Oopsies, I don't know what that means!");
            ui.printline();
        }
        return true;
    }
}
