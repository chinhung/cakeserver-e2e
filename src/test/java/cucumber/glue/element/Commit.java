package cucumber.glue.element;

public class Commit {

    private String message;
    private String note;

    public Commit(String message, String note) {
        this.message = message;
        this.note = note;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
