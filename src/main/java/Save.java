import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.io.FileWriter;

public class Save {
    private ArrayList<Task> data = new ArrayList<>();
    private File saveFile = new File("./data/Peppa.txt");

    Save() throws IOException { //creating a new instance of Save will try to create a saveFile
        if (saveFile.exists()) {
            readFromSaveFile();
        } else {
            saveFile.createNewFile();
        }
    }

    public boolean saveToHardDrive(ArrayList<Task> tasks) throws IOException {
        if (saveFile.exists()) {
            saveFile.delete();
        }
        saveFile.createNewFile();
        try {
            FileWriter writer = new FileWriter(saveFile);
            for (int i = 0; i < tasks.size(); i++) {
                String saveFileDesc = tasks.get(i).toSaveFileFormat();
                writer.write(saveFileDesc+"\n");
            }
            writer.close();
            System.out.println("Successfully saved to ./data/Peppa.txt");
            return true;
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }

    public boolean readFromSaveFile() throws IOException {
        if (!saveFile.exists()) {
            return false;
        }
        try {
            Scanner scanner = new Scanner(saveFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] splitLine = line.split(" | ");

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
                    this.data.add(newTask);
                }
            }
            scanner.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return false;
        } catch (SaveFileCorruptedException e) {
            System.out.println(e);
            return false;
        }
    }
}
