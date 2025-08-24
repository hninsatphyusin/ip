import java.util.Scanner;

public class Peppa {
    private static final String LINE = "____________________________________________________________";
    private static final String NAME = "Peppa";
    private static boolean QUIT = false;
    private static Task[] Tasks = new Task[100];
    private static int size = 0;
    private static final int MAXSIZE = 100;


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

    private static void addTask(Task task) {
        Tasks[size] = task;
        size++;
        System.out.println("Added: " + task.description);
        printline();
    }

    private static void displayTasks() {
        for (int i = 0; i < size; i++) {
            int num = i+1;
            System.out.println(num+". "+Tasks[i].description);
        }
        printline();
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
                QUIT=true;
                break;
            } else if (command.equals("list")) {
                displayTasks();
            } else {
                Task newTask = new Task(command);
                addTask(newTask);
            }
        }

        exit();
        printline();
    }
}
