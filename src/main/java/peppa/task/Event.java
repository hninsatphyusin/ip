package peppa.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Task that spans a time range rather than a single deadline.
 * Stores both start and end {@link LocalDateTime} values.
 */
public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Creates a new event.
     *
     * @param description free-text details of the event
     * @param from start date-time in <code>d/M/yyyy&nbsp;HHmm</code> format (e.g.&nbsp;<kbd>12/9/2025&nbsp;1300</kbd>)
     * @param to   end   date-time in the same format
     */
    public Event(String description, String from, String to) {
        super(description);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        this.from = LocalDateTime.parse(from, fmt);
        this.to = LocalDateTime.parse(to, fmt);
    }

    /**
     * Serialises the event using the custom save-file layout:
     * <pre>E | &lt;0 or 1&gt; | &lt;description&gt; | &lt;from&nbsp;d/M/yyyy&nbsp;HHmm&gt; | &lt;to&nbsp;d/M/yyyy&nbsp;HHmm&gt;</pre>
     *
     * @return one-line representation for disk storage
     */
    @Override
    public String toSaveFileFormat() {
        String data = "E | ";
        if (this.isDone()) {
            data += "1 | ";
        } else {
            data += "0 | ";
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        data += this.description + " | " + this.from.format(fmt) + " | " + this.to.format(fmt);
        return data;
    }

    /**
     * Pretty-prints the task for CLI display, e.g.
     * <code>[E]project meeting (from: Sep&nbsp;12&nbsp;2025&nbsp;1 PM&nbsp;to:&nbsp;Sep&nbsp;12&nbsp;2025&nbsp;3 PM)</code>
     *
     * @return human-friendly description containing the date range
     */
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM d yyyy h a");
        return "[E]" + super.toString() + " (from: " + this.from.format(fmt) + " to: " + this.to.format(fmt) + ")";
    }
}
