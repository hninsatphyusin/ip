package peppa.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private static final String INPUT_DATE_FORMAT = "d/M/yyyy HHmm";
    private static final String OUTPUT_DATE_FORMAT = "MMM d yyyy h a";
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, String from, String to) {
        super(description);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT);
        this.from = LocalDateTime.parse(from, fmt);
        this.to = LocalDateTime.parse(to, fmt);
    }

    @Override
    public String toSaveFileFormat() {
        String data = "E | ";
        if (this.isDone()) {
            data += "1 | ";
        } else {
            data += "0 | ";
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT);
        data += this.description + " | " + this.from.format(fmt) + " | " + this.to.format(fmt);
        return data;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(OUTPUT_DATE_FORMAT);
        return "[E]" + super.toString() + " (from: " + this.from.format(fmt) + " to: " + this.to.format(fmt) + ")";
    }
}
