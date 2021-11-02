package cucumber.glue.element;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
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
        return findCommit(message).isPresent();
    }

    public boolean commitExists(String message, String note) {
        return findCommit(message, note).isPresent();
    }

    private Optional<Commit> findCommit(String message) {
        List<Commit> commits = findAllCommits();
        return commits.stream()
                .filter(c -> c.getMessage().equals(message))
                .findAny();
    }

    private Optional<Commit> findCommit(String message, String note) {
        List<Commit> commits = findAllCommits();
        return commits.stream()
                .filter(c -> c.getMessage().equals(message))
                .filter(c -> c.getNote().equals(note))
                .findAny();
    }

    private List<Commit> findAllCommits() {
        List<WebElement> commitMessages = driver.findElements(commitMessageSelector);
        List<WebElement> commitNotes = driver.findElements(commitNoteSelector);

        List<Commit> result = new ArrayList<>();
        for (int i = 0; i < commitMessages.size(); i++) {
            result.add(new Commit(commitMessages.get(i), commitNotes.get(i)));
        }

        return result;
    }

    public void selectCommitWithMessageAndNote(String message, String note) {
        Optional<Commit> commit = findCommit(message, note);

        if (!commit.isPresent()) {
            throw new NotFoundException("commit with message: " + message + " and note: " + note + " not found");
        }

        commit.get().select();
    }
}
