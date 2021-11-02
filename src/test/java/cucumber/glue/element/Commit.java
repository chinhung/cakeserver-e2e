package cucumber.glue.element;

import org.openqa.selenium.WebElement;

public class Commit {

    private WebElement message;
    private WebElement note;

    public Commit(WebElement message, WebElement note) {
        this.message = message;
        this.note = note;
    }

    public String getMessage() {
        return message.getText();
    }

    public String getNote() {
        return note.getText();
    }

    public void select() {
        message.click();
    }
}
