package cucumber.glue.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextField extends ElementObject {

    private By selector;

    public TextField(WebDriver driver, By selector) {
        super(driver);
        this.selector = selector;
    }

    public void fill(String text) {
        WebElement element = driver.findElement(selector);
        element.sendKeys(text);
    }

    public String grab() {
        WebElement element = driver.findElement(selector);
        return element.getText();
    }
}
