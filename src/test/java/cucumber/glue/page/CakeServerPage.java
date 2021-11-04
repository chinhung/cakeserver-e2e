package cucumber.glue.page;

import cucumber.glue.element.Button;
import cucumber.glue.element.CommitList;
import cucumber.glue.element.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

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

    public boolean commitExists(String message, String note) {
        return commitList.commitExists(message, note);
    }

    public void selectCommitWithMessageAndNote(String message, String note) {
        commitList.selectCommitWithMessageAndNote(message, note);
    }

    public void enterCommitMessage(String message) {
        messageField.fill(message);
    }

    public void enterCommitNote(String note) {
        noteField.fill(note);
    }

    public void saveCommit() {
        saveBtn.click();
    }

    public void selectNewCommit() {
        newCommitBtn.click();
    }

    public void redoCommit() {
        redoBtn.click();
    }

    public String currentCommitMessage() {
        return messageField.grab();
    }

    public String currentCommitNote() {
        return noteField.grab();
    }

    public void copyCommitMessage() {
        copyCommitMessageBtn.click();
    }

    public void enterCommitNoteFromClipboard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String fromClipboard;
        try {
            fromClipboard = (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        noteField.fill(fromClipboard);
    }

    public void deleteCommit() {
        deleteBtn.click();
    }
}
