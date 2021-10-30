package cucumber.glue.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Button {

    private WebDriver driver;
    private By selector;

    public Button(WebDriver driver, By selector) {
        this.driver = driver;
        this.selector = selector;
    }

    public void click() {
        WebElement element = driver.findElement(selector);
        element.click();
    }
}
