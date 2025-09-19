package peppa.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Task that must be finished by a specific date-time.
 * Stores the deadline and formats itself for display and persistence.
 */
public class Deadline extends Task {
    private static final String INPUT_DATE_FORMAT = "d/M/yyyy HHmm";
    private static final String OUTPUT_DATE_FORMAT = "MMM d yyyy h a";
    protected LocalDateTime by;

    /**
     * Creates a new deadline task.
     *
     * @param description human-readable task details
     * @param by due date-time in <code>d/M/yyyy&nbsp;HHmm</code> format
     *           (e.g.&nbsp;<kbd>2/9/2025&nbsp;2359</kbd>)
     */
    public Deadline(String description, String by) {
        super(description);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT);
        this.by = LocalDateTime.parse(by, fmt);
    }


    /**
     * Encodes the task into the custom save-file syntax:
     * <pre>D | &lt;0 or 1&gt; | &lt;description&gt; | &lt;d/M/yyyy&nbsp;HHmm&gt;</pre>
     *
     * @return one-line representation ready to be written to disk
     */
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

    /**
     * Human-friendly view of the task, e.g.
     * <code>[D]read book(by: Sep&nbsp;2&nbsp;2025&nbsp;11&nbsp;PM)</code>
     *
     * @return pretty-printed string for CLI display
     */
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(OUTPUT_DATE_FORMAT);
        return "[D]" + super.toString() + "(by: " + this.by.format(fmt) + ")";
    }

    @Override
    public LocalDateTime getDateTime() {
        return this.by; // Use the deadline for sorting
    }

    @Override
    public int compareTo(Task other) {
        LocalDateTime otherDateTime = other.getDateTime();
        
        // If the other task has no date/time, this deadline comes before it
        if (otherDateTime == null) {
            return -1;
        }
        
        // Both tasks have date/time - compare by date/time
        return this.by.compareTo(otherDateTime);
    }
}

