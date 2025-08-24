import java.util.Scanner;

public class Peppa {
    private static String LINE = "____________________________________________________________";
    private static String NAME = "Peppa";
    private static boolean QUIT = false;

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

        while (!QUIT) {
            String command = scanner.nextLine();
            if (command.equals("bye")) {
                QUIT=true;
                break;
            }
            System.out.println(command);
            printline();
        }

        exit();
        printline();
    }
}
