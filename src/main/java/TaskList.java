import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private int size = 0;
    private Ui ui;

    TaskList(ArrayList<Task> tasks, Ui ui) {
        this.tasks = tasks;
        this.ui = ui;
    }

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
            size++;
            System.out.println("Got it. I've: added this task: ");
            System.out.println(newTask);
            System.out.println("Now you have " + size + " tasks in the list.");
            ui.printline();
            return true;
        } else {
            ui.printline();
            return false;
        }
    }

    public void displayTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            int num = i+1;
            System.out.println(num+"."+ tasks.get(i));
        }
        ui.printline();
    }

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

    public boolean unmarkTask(int num) {
        if (num < size) {
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

    public boolean deleteTask(int num) {
        if (num < size) {
            Task tbr = tasks.remove(num);
            System.out.println("Noted. I've removed this task:");
            System.out.println(tbr);
            size--;
            System.out.println("Now you have " + size + " in the list");
            ui.printline();
            return true;
        }
        System.out.println("Cannot delete task because task does not exist!");
        ui.printline();
        return false;
    }

    public void setTaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTaskList() {
        return this.tasks;
    }

}
