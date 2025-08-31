package peppa.task;

public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toSaveFileFormat() {
        String data = "T | ";
        if (this.isDone()) {
            data += "1 | ";
        } else {
            data += "0 | ";
        }
        data += this.description;
        return data;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
