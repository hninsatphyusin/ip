package peppa.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private static final String INPUT_DATE_FORMAT = "d/M/yyyy HHmm";
    private static final String OUTPUT_DATE_FORMAT = "MMM d yyyy h a";
    protected LocalDateTime by;

    public Deadline(String description, String by) {
        super(description);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT);
        this.by = LocalDateTime.parse(by, fmt);
    }

    @Override
    public String toSaveFileFormat() {
        String data = "D | ";
        if (this.isDone()) {
            data += "1 | ";
        } else {
            data += "0 | ";
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT);
        data += this.description + " | " + this.by.format(fmt);
        return data;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(OUTPUT_DATE_FORMAT);
        return "[D]" + super.toString() + "(by: " + this.by.format(fmt) + ")";
    }
}

