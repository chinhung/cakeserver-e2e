package cucumber.glue;

import cucumber.glue.page.CakeServerPage;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@ScenarioScoped
public class CakeServerStepdefs {

    WebDriver driver;

    CakeServerPage cakeServerPage;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "./webdriver/chromedriver");
        driver = new ChromeDriver();
        cakeServerPage = new CakeServerPage(driver, "https://chinhung.github.io/cakeserver/");
        cakeServerPage.open();
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Given("^commit with message: (.*) and note: (.*) exists$")
    public void commitWithMessageAndNoteExists(String message, String note) {
        assertTrue(cakeServerPage.commitExists(message, note));
    }

    @Given("^commit with message: (.*) does not exist$")
    public void commitWithMessageDoesNotExist(String message) {
        assertFalse(cakeServerPage.commitExists(message));
    }

    @When("^create commit with message: (.*) and note: (.*)$")
    public void createCommitWithMessageAndNote(String message, String note) {
        cakeServerPage.createNewCommit(message, note);
    }

    @When("^select commit with message: (.*) and note: (.*)$")
    public void selectCommitWithMessageAndNote(String message, String note) {
        cakeServerPage.selectCommitWithMessageAndNote(message, note);
    }

    @When("^enter commit message: (.*)")
    public void enterCommitMessage(String message) {
        cakeServerPage.enterCommitMessage(message);
    }

    @When("^enter commit note: (.*)")
    public void enterCommitNote(String note) {
        cakeServerPage.enterCommitNote(note);
    }

    @When("save commit")
    public void saveCommit() {
        cakeServerPage.saveCommit();
    }
}
