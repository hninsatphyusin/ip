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
    Parser(TaskList tasks, Storage storage, Ui ui) {
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
    public boolean parse(String input) { //returns false if quitting
        input = input.trim(); //removes whitespace from both ends of a string
        if (input.equals("bye")) {
            return false;
        } else if (input.equals("list")) {
            tasks.displayTasks();
        } else if (input.contains("unmark")) {
            String[] arr = input.split(" ");
            tasks.unmarkTask(Integer.valueOf(arr[1])-1);
            storage.save(tasks);
        } else if (input.contains("mark")) {
            String[] arr = input.split(" ");
            tasks.markTask(Integer.valueOf(arr[1])-1);
            storage.save(tasks);
        } else if (input.contains("delete")) {
            String[] arr = input.split(" ");
            tasks.deleteTask(Integer.valueOf(arr[1])-1);
            storage.save(tasks);
        } else if (input.contains("todo") || input.contains("deadline") || input.contains("event")) {
            tasks.addTask(input);
            storage.save(tasks);
        } else {
            System.out.println("Oopsies, I don't know what that means!");
            ui.printline();
        }
        return true;
    }
}
