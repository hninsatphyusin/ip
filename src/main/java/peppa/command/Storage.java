package peppa.command;

import peppa.exception.SaveFileCorruptedException;
import peppa.task.Task;
import peppa.task.TaskList;
import peppa.task.ToDo;
import peppa.task.Event;
import peppa.task.Deadline;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.io.FileWriter;

public class Storage {
    private final File filePath;

    public Storage(String filePath) { //creating a new instance of Save will try to create a saveFile
        this.filePath = new File(filePath);
        try {
            if (this.filePath.exists()) {
                load();
            } else {
                File parentDir = this.filePath.getParentFile();
                if (!parentDir.exists()) {
                    parentDir.mkdirs();
                }
                this.filePath.createNewFile();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public boolean save(TaskList tasks) {
        ArrayList<Task> tl = tasks.getTaskList();
        try {
            if (filePath.exists()) {
                filePath.delete();
            }
            filePath.createNewFile();
            FileWriter writer = new FileWriter(filePath);
            for (int i = 0; i < tl.size(); i++) {
                String saveFileDesc = tl.get(i).toSaveFileFormat();
                writer.write(saveFileDesc+"\n");
            }
            writer.close();
            System.out.println("Successfully saved to ./data/peppa.command.Peppa.txt");
            return true;
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Task> load() throws IOException {
        if (!filePath.exists()) {
            return null;
        }
        try {
            ArrayList<Task> data = new ArrayList<>();
            Scanner scanner = new Scanner(filePath);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] splitLine = line.split(" \\| ");

                Task newTask;
                switch (splitLine[0]) {
                    case "T":
                        newTask = new ToDo(splitLine[2]);
                        break;
                    case "E":
                        newTask = new Event(splitLine[2], splitLine[3], splitLine[4]);
                        break;
                    case "D":
                        newTask = new Deadline(splitLine[2], splitLine[3]);
                        break;
                    default:
                        newTask = null;
                        throw new SaveFileCorruptedException();

                }
                if ( newTask != null ) {
                    if (Objects.equals(splitLine[1], "1")) {
                        newTask.markAsDone();
                    }
                    data.add(newTask);
                }
            }
            scanner.close();
            return data;
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return null;
        } catch (SaveFileCorruptedException e) {
            System.out.println(e);
            return null;
        }
    }
}
