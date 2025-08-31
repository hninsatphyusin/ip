package peppa.task;

import peppa.ui.Ui;

import java.util.ArrayList;

/**
 * In-memory catalogue of {@link Task}s, offering add / list / mark / unmark / delete operations
 * while delegating all console output decorations to a {@link Ui} instance.
 */
public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private Ui ui;

    /**
     * Wraps an existing task collection with a UI helper.
     *
     * @param tasks initial task data to manage
     * @param ui    CLI decorator responsible for printing divider lines
     */
    public TaskList(ArrayList<Task> tasks, Ui ui) {
        this.tasks = tasks;
        this.ui = ui;
    }

    /**
     * Parses a raw user command (<em>todo</em>, <em>deadline</em>, or <em>event</em>)
     * into a concrete {@link Task} and appends it to the list.
     *
     * @param task full command string typed by the user
     * @return {@code true} if parsing succeeded and the task was stored;
     *         {@code false} for malformed input
     */
    public boolean addTask(String task) {
        Task newTask;
        if (task.contains("todo")) {
            newTask = new ToDo(task.substring(5, task.length())); //remove the todo
        } else if (task.contains("deadline")) {
            String str = task.substring(9); //remove the deadline
            String[] arr = str.split("/by "); //split into description and deadline
            newTask = new Deadline(arr[0], arr[1]); //create a newTask
        } else if (task.contains("event")) {
            String str =  task.substring(6); //remove the deadline

            int from = str.indexOf("/from");
            int to = str.indexOf("/to");

            String description = str.substring(0, from-1);
            String start = str.substring(from+6, to-1);
            String end = str.substring(to+4);

            newTask = new Event(description, start, end);
        } else {
            newTask = null;
        }
        if (newTask!=null) {
            tasks.add(newTask);
            System.out.println("Got it. I've: added this task: ");
            System.out.println(newTask);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            ui.printline();
            return true;
        } else {
            ui.printline();
            return false;
        }
    }

    /**
     * Prints every task with a 1-based index, then a divider line.
     */
    public void displayTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            int num = i+1;
            System.out.println(num+"."+ tasks.get(i));
        }
        ui.printline();
    }

    /**
     * Marks the given task as done and confirms the action.
     *
     * @param num zero-based task index
     * @return {@code true} on success, {@code false} if the index is out of range
     */
    public boolean markTask(int num) {
        if (num < tasks.size()) {
            tasks.get(num).markAsDone();
            System.out.println("Nice! I've marked this task as done: ");
            System.out.println(tasks.get(num));
            ui.printline();
            return true;
        }
        System.out.println("Cannot unmark task because task does not exist!");
        ui.printline();
        return false;
    }

    /**
     * Reverses the done state of the given task and confirms the action.
     *
     * @param num zero-based task index
     * @return {@code true} on success, {@code false} if the index is out of range
     */
    public boolean unmarkTask(int num) {
        if (num < tasks.size()) {
            tasks.get(num).markAsUndone();
            System.out.println("OK, I've marked this task as not done yet: ");
            System.out.println(tasks.get(num));
            ui.printline();
            return true;
        }
        System.out.println("Cannot unmark task because task does not exist!");
        ui.printline();
        return false;
    }

    /**
     * Removes the indexed task from the list and prints a summary.
     *
     * @param num zero-based task index
     * @return {@code true} on success, {@code false} if the index is out of range
     */
    public boolean deleteTask(int num) {
        if (num < tasks.size()) {
            Task tbr = tasks.remove(num);
            System.out.println("Noted. I've removed this task:");
            System.out.println(tbr);
            System.out.println("Now you have " + tasks.size() + " in the list");
            ui.printline();
            return true;
        }
        System.out.println("Cannot delete task because task does not exist!");
        ui.printline();
        return false;
    }

    /**
     * Replaces the current internal list with the supplied one.
     *
     * @param tasks new task collection
     */
    public void setTaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Exposes the underlying mutable list for persistence.
     *
     * @return the current {@link ArrayList} of tasks
     */
    public ArrayList<Task> getTaskList() {
        return this.tasks;
    }

}
