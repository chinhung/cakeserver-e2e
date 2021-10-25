package cucumber.glue.element;

import org.openqa.selenium.WebDriver;

public class ElementObject {

    protected WebDriver driver;

    public ElementObject(WebDriver driver) {
        this.driver = driver;
    }
}
