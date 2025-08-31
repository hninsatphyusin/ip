public class SaveFileCorruptedException extends RuntimeException {
  public SaveFileCorruptedException() {
    super("Save file is corrupted");
  }

  public SaveFileCorruptedException(String message) {
    super(message);
  }
}
