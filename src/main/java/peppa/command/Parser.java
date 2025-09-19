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
        assert tasks != null : "TaskList should not be null";
        assert storage != null : "Storage should not be null";
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
        assert input != null : "Input command should not be null";
        input = input.trim();
        assert !input.isEmpty() : "Input command should not be empty after trimming";
        String command = input.split("\\s+", 2)[0];
        switch (command) {
            case "bye":
                return "Bye. Hope to see you again soon!";
            case "list":
                return tasks.displayTasks();
            case "unmark": {
                String[] parts = input.split(" ");
                assert parts.length > 1 : "Unmark command should have an index argument";
                int idx = Integer.parseInt(parts[1]) - 1;
                assert idx >= 0 : "Task index for unmark should be non-negative";
                String result = tasks.unmarkTask(idx);
                storage.save(tasks);
                return result;
            }
            case "mark": {
                String[] parts = input.split(" ");
                assert parts.length > 1 : "Mark command should have an index argument";
                int idx = Integer.parseInt(parts[1]) - 1;
                assert idx >= 0 : "Task index for mark should be non-negative";
                String result = tasks.markTask(idx);
                storage.save(tasks);
                return result;
            }
            case "delete": {
                String[] parts = input.split(" ");
                assert parts.length > 1 : "Delete command should have an index argument";
                int idx = Integer.parseInt(parts[1]) - 1;
                assert idx >= 0 : "Task index for delete should be non-negative";
                String result = tasks.deleteTask(idx);
                storage.save(tasks);
                return result;
            }
            case "find": {
                String[] parts = input.split("\\s+", 2);
                assert parts.length > 1 : "Find command should have a search argument";
                String toFind = parts[1];
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
