import java.util.Scanner;

public class Peppa {
    private static final String LINE = "____________________________________________________________";
    private static final String NAME = "Peppa";
    private static boolean QUIT = false;
    private static final int MAXSIZE = 100;
    private static Task[] Tasks = new Task[MAXSIZE];
    private static int size = 0;

    private static void greeting() {
        String str = "Hello! I'm " + Peppa.NAME + "!\nWhat can I do for you?";
        System.out.println(str);
    }
    private static void printline() {
        System.out.println(Peppa.LINE);
    }

    private static void exit() {
        String str = "Bye. Hope to see you again soon!";
        System.out.println(str);
    }

    private static boolean addTask(String task) {
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
            String end = str.substring(to+3);

            newTask = new Event(description, start, end);
        } else {
            newTask = null;
        }
        if (size+1<MAXSIZE && newTask!=null) {
            Tasks[size] = newTask;
            size++;
            System.out.println("Got it. I've: added this task: ");
            System.out.println(newTask);
            System.out.println("Now you have " + size + " tasks in the list.");
            printline();
            return true;
        } else {
            printline();
            return false;
        }
    }

    private static void displayTasks() {
        for (int i = 0; i < size; i++) {
            int num = i+1;
            System.out.println(num+"."+Tasks[i]);
        }
        printline();
    }

    private static boolean markTask(int num) {
        if (num < size) {
            Tasks[num].markAsDone();
            System.out.println("Nice! I've marked this task as done: ");
            System.out.println(Tasks[num]);
            printline();
            return true;
        }
        return false;
    }

    private static boolean unmarkTask(int num) {
        if (num < size) {
            Tasks[num].markAsUndone();
            System.out.println("OK, I've marked this task as not done yet: ");
            System.out.println(Tasks[num]);
            printline();
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String logo = ".______    _______ .______   .______      ___      \n"
                + "|   _  \\  |   ____||   _  \\  |   _  \\    /   \\     \n"
                + "|  |_)  | |  |__   |  |_)  | |  |_)  |  /  ^  \\    \n"
                + "|   ___/  |   __|  |   ___/  |   ___/  /  /_\\  \\   \n"
                + "|  |      |  |____ |  |      |  |     /  _____  \\  \n"
                + "| _|      |_______|| _|      | _|    /__/     \\__\\\n";
        System.out.println(logo);
        printline();
        greeting();
        printline();

        while (!QUIT) {
            String command = scanner.nextLine();
            if (command.equals("bye")) {
                QUIT = true;
                break;
            } else if (command.equals("list")) {
                displayTasks();
            } else if (command.contains("unmark")) {
                String[] arr = command.split(" ");
                unmarkTask(Integer.valueOf(arr[1])-1);
            } else if (command.contains("mark")) {
                String[] arr = command.split(" ");
                markTask(Integer.valueOf(arr[1])-1);
            } else if (command.contains("todo") || command.contains("deadline") || command.contains("event")) {
                addTask(command);
            } else {
                System.out.println("Oopsies, I don't know what that means!");
                printline();
            }
        }

        exit();
        printline();
    }
}
