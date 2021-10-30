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

    @Given("^commit exists: (.*)$")
    public void commitExists(String message) {
        assertTrue(cakeServerPage.commitExists(message));
    }

    @Given("^commit does not exist: (.*)$")
    public void commitDoesNotExist(String message) {
        assertFalse(cakeServerPage.commitExists(message));
    }

    @When("^create commit with message: (.*) and note: (.*)$")
    public void createCommitWithMessageAndNote(String message, String note) {
        cakeServerPage.createNewCommit(message, note);
    }
}
