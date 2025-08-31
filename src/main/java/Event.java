public class Event extends Task {

    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toSaveFileFormat() {
        String data = "E | ";
        if (this.isDone()) {
            data += "1 | ";
        } else {
            data += "0 | ";
        }
        data += this.description + " | " + this.from + " | " + this.to;
        return data;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to:" + this.to + ")";
    }
}
