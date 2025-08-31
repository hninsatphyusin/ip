package peppa.ui;

public class Ui {
    private final String LINE = "____________________________________________________________";
    private final String NAME = "peppa.command.Peppa";

    private final String logo = ".______    _______ .______   .______      ___      \n"
            + "|   _  \\  |   ____||   _  \\  |   _  \\    /   \\     \n"
            + "|  |_)  | |  |__   |  |_)  | |  |_)  |  /  ^  \\    \n"
            + "|   ___/  |   __|  |   ___/  |   ___/  /  /_\\  \\   \n"
            + "|  |      |  |____ |  |      |  |     /  _____  \\  \n"
            + "| _|      |_______|| _|      | _|    /__/     \\__\\\n";


    public void greeting() {
        String str = "Hello! I'm " + NAME + "!\nWhat can I do for you?";
        System.out.println(str);
    }
    public void printline() {
        System.out.println(LINE);
    }

    public void printLogo() {
        System.out.println(logo);
    }
    public static void exitMsg() {
        String str = "Bye. Hope to see you again soon!";
        System.out.println(str);
    }
}
