package cucumber.glue.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommitList {

    private WebDriver driver;
    private By commitMessageSelector;
    private By commitNoteSelector;

    public CommitList(WebDriver driver, By commitMessageSelector, By commitNoteSelector) {
        this.driver = driver;
        this.commitMessageSelector = commitMessageSelector;
        this.commitNoteSelector = commitNoteSelector;
    }

    public boolean commitExists(String message) {
        List<Commit> commits = findCommits();
        Optional<Commit> commit = commits.stream().filter(c -> c.getMessage().equals(message)).findAny();

        return commit.isPresent();
    }

    public boolean commitExists(String message, String note) {
        List<Commit> commits = findCommits();
        Optional<Commit> commit = commits.stream().filter(c -> c.getMessage().equals(message)).findAny();

        if (!commit.isPresent()) {
            return false;
        }
        if (!commit.get().getNote().equals(note)) {
            return false;
        }
        return true;
    }

    private List<Commit> findCommits() {
        List<WebElement> commitMessages = driver.findElements(commitMessageSelector);
        List<WebElement> commitNotes = driver.findElements(commitNoteSelector);

        List<Commit> result = new ArrayList<>();
        for (int i = 0; i < commitMessages.size(); i++) {
            result.add(new Commit(commitMessages.get(i).getText(), commitNotes.get(i).getText()));
        }

        return result;
    }
}
