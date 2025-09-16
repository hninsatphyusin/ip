package peppa.command;

import peppa.task.TaskList;
// import peppa.ui.Ui; (no longer needed)

/**
 * Parses a raw user command and triggers the matching TaskList / Storage / Ui action.
 */
public class Parser {
    private TaskList tasks;
    private final Storage storage;

    /**
     * Creates a parser bound to the given collaborators.
     *
     * @param tasks    mutable task catalogue to operate on
     * @param storage  persistence layer used for serialising {@code tasks}
     * @param ui       user-interface helper for printing separators / prompts
     */
    public Parser(TaskList tasks, Storage storage) {
        this.tasks = tasks;
        this.storage = storage;
    }

    /**
     * Analyse a single command line and execute the matching action.
     *
     * @param input raw command string typed by the user
     * @return {@code false} if the user typed {@literal "bye"} (caller should terminate);
     *         {@code true} otherwise
     */
    public String parse(String input) {
        input = input.trim();
        String command = input.split("\\s+", 2)[0];
        switch (command) {
            case "bye":
                return "Bye. Hope to see you again soon!";
            case "list":
                return tasks.displayTasks();
            case "unmark": {
                String[] parts = input.split(" ");
                String result = tasks.unmarkTask(Integer.parseInt(parts[1]) - 1);
                storage.save(tasks);
                return result;
            }
            case "mark": {
                String[] parts = input.split(" ");
                String result = tasks.markTask(Integer.parseInt(parts[1]) - 1);
                storage.save(tasks);
                return result;
            }
            case "delete": {
                String[] parts = input.split(" ");
                String result = tasks.deleteTask(Integer.parseInt(parts[1]) - 1);
                storage.save(tasks);
                return result;
            }
            case "find": {
                String toFind = input.split("\\s+", 2)[1];
                return tasks.findTask(toFind);
            }
            case "todo":
            case "deadline":
            case "event": {
                String result = tasks.addTask(input);
                storage.save(tasks);
                return result;
            }
            default:
                return "Oopsies, I don't know what that means!";
        }
    }
}
