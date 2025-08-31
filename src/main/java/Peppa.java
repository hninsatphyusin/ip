import java.io.IOException;
import java.util.Scanner;


public class Peppa {
    private static boolean QUIT = false;
    private static Ui ui = new Ui();
    private static TaskList tasks;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage("./data/Peppa.txt");
        try {
            tasks = new TaskList(storage.load(), ui);
        } catch (IOException e) {
            System.out.println(e);
        }
        ui.printline();
        ui.printLogo();
        ui.greeting();
        ui.printline();
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
                storage.save(tasks);
            } else if (command.contains("mark")) {
                String[] arr = command.split(" ");
                tasks.markTask(Integer.valueOf(arr[1])-1);
                storage.save(tasks);
            } else if (command.contains("delete")) {
                String[] arr = command.split(" ");
                tasks.deleteTask(Integer.valueOf(arr[1])-1);
                storage.save(tasks);
            } else if (command.contains("todo") || command.contains("deadline") || command.contains("event")) {
                tasks.addTask(command);
                storage.save(tasks);
            } else {
                System.out.println("Oopsies, I don't know what that means!");
                ui.printline();
            }
        }

        ui.exitMsg();
        ui.printline();
    }
}
