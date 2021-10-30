package cucumber.glue.page;

import cucumber.glue.element.Button;
import cucumber.glue.element.CommitList;
import cucumber.glue.element.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CakeServerPage {

    private WebDriver driver;
    private String url;

    private Button newCommitBtn;
    private CommitList commitList;
    private TextField messageField;
    private TextField noteField;
    private Button saveBtn;
    private Button redoBtn;
    private Button copyCommitMessageBtn;
    private Button deleteBtn;

    public CakeServerPage(WebDriver driver, String url) {
        this.driver = driver;
        this.url = url;
        this.newCommitBtn = new Button(driver, By.className("e2e-new-commit"));
        this.commitList = new CommitList(driver, By.className("e2e-commit-message"), By.className("e2e-commit-note"));
        this.messageField = new TextField(driver, By.className("e2e-message"));
        this.noteField = new TextField(driver, By.className("e2e-note"));
        this.saveBtn = new Button(driver, By.className("e2e-save"));
        this.redoBtn = new Button(driver, By.className("e2e-redo"));
        this.copyCommitMessageBtn = new Button(driver, By.className("e2e-copy-commit-message"));
        this.deleteBtn = new Button(driver, By.className("e2e-delete"));
    }

    public void open() {
        driver.get(url);
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
