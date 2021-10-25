package cucumber.glue.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Button extends ElementObject {

    private By selector;

    public Button(WebDriver driver, By selector) {
        super(driver);
        this.selector = selector;
    }

    public void click() {
        WebElement element = driver.findElement(selector);
        element.click();
    }
}
