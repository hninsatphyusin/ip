import java.util.Scanner;
import java.util.ArrayList;


public class Peppa {
    private static boolean QUIT = false;
    private static Ui ui = new Ui();
    private static TaskList tasks = new TaskList(ui);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ui.printline();
        ui.printLogo();
        ui.greeting();
        ui.printline();

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
                ui.printline();
            }
        }

        ui.exitMsg();
        ui.printline();
    }
}
