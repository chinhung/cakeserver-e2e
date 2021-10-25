package cucumber.glue.page;

import cucumber.glue.element.Button;
import cucumber.glue.element.CommitList;
import cucumber.glue.element.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CakeServerPage extends PageObject {

    Button newCommitBtn;

    CommitList commitList;

    TextField messageField;

    TextField noteField;

    Button saveBtn;

    Button redoBtn;

    Button copyCommitMessageBtn;

    Button deleteBtn;

    public CakeServerPage(WebDriver driver) {
        super(driver);
        newCommitBtn = new Button(driver, By.className("e2e-new-commit"));
        commitList = new CommitList(driver, By.className("e2e-commit-message"), By.className("e2e-commit-note"));
        messageField = new TextField(driver, By.className("e2e-message"));
        noteField = new TextField(driver, By.className("e2e-note"));
        saveBtn = new Button(driver, By.className("e2e-save"));
        redoBtn = new Button(driver, By.className("e2e-redo"));
        copyCommitMessageBtn = new Button(driver, By.className("e2e-copy-commit-message"));
        deleteBtn = new Button(driver, By.className("e2e-delete"));
    }

    public void open() {
        driver.get("https://chinhung.github.io/cakeserver/");
    }

    public void createNewCommit(String message, String note) {
        newCommitBtn.click();
        messageField.fill(message);
        noteField.fill(note);
        saveBtn.click();
    }

    public boolean commitExists(String message) {
        return commitList.commitExists(message);
    }
}
