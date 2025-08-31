import java.util.Scanner;
import java.util.ArrayList;


public class Peppa {
    private static final String LINE = "____________________________________________________________";
    private static final String NAME = "Peppa";
    private static boolean QUIT = false;
    private static TaskList tasks = new TaskList();

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

        Save saveFile = new Save();
        ArrayList<Task> data = saveFile.getTasksFromSaveFile();
        if (data != null && data.size()>0) {
            tasks.setTaskList(data);
        }

        while (!QUIT) {
            String command = scanner.nextLine();
            if (command.equals("bye")) {
                QUIT = true;
                break;
            } else if (command.equals("list")) {
                tasks.displayTasks();
            } else if (command.contains("unmark")) {
                String[] arr = command.split(" ");
                tasks.unmarkTask(Integer.valueOf(arr[1])-1);
                saveFile.saveToHardDrive(tasks);
            } else if (command.contains("mark")) {
                String[] arr = command.split(" ");
                tasks.markTask(Integer.valueOf(arr[1])-1);
                saveFile.saveToHardDrive(tasks);
            } else if (command.contains("delete")) {
                String[] arr = command.split(" ");
                tasks.deleteTask(Integer.valueOf(arr[1])-1);
                saveFile.saveToHardDrive(tasks);
            } else if (command.contains("todo") || command.contains("deadline") || command.contains("event")) {
                tasks.addTask(command);
                saveFile.saveToHardDrive(tasks);
            } else {
                System.out.println("Oopsies, I don't know what that means!");
                printline();
            }
        }

        exit();
        printline();
    }
}
