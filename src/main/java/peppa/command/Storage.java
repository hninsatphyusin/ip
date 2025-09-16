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

/**
 * Persists the user’s tasks: saves the current list to disk and recreates it on start-up.
 * Internally wraps a {@link java.io.File} and handles both serialization and parsing.
 */
public class Storage {
    private final File file;

    /**
     * Builds a Storage backed by the given path and guarantees that
     * the file (and its parent directory) exist, loading any data found.
     *
     * @param filePath location of the save file relative to the project root
     */
    public Storage(String filePath) { //creating a new instance of Save will try to create a saveFile
        this.file = new File(filePath);
        try {
            if (this.file.exists()) {
                load();
            } else {
                File parentDir = this.file.getParentFile();
                if (!parentDir.exists()) {
                    parentDir.mkdirs();
                }
                this.file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Serialises every task in {@code tasks} to the backing file,
     * overwriting any previous contents.
     *
     * @param tasks current in-memory task list
     * @return {@code true} on success, {@code false} if any I/O error occurs
     */
    public boolean save(TaskList tasks) {
        ArrayList<Task> currentTasks = tasks.getTaskList();
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < currentTasks.size(); i++) {
                String saveFileDesc = currentTasks.get(i).toSaveFileFormat();
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

    /**
     * Reads the backing file, reconstructs all tasks it contains and
     * returns them as a list; returns {@code null} if the file is missing
     * or irreparably corrupted.
     *
     * @return list of tasks recovered from disk, or {@code null} on failure
     * @throws IOException if a low-level I/O error prevents reading the file
     */
    public ArrayList<Task> load() throws IOException {
        if (!file.exists()) {
            return null;
        }
        try {
            ArrayList<Task> data = new ArrayList<>();
            Scanner scanner = new Scanner(file);
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
