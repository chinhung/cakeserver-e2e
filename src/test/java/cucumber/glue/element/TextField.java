package cucumber.glue.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextField {

    private WebDriver driver;
    private By selector;

    public TextField(WebDriver driver, By selector) {
        this.driver = driver;
        this.selector = selector;
    }

    public void fill(String text) {
        WebElement element = driver.findElement(selector);
        element.clear();
        element.sendKeys(text);
    }

    public String grab() {
        WebElement element = driver.findElement(selector);
        return element.getAttribute("value");
    }

    public void clear() {
        WebElement element = driver.findElement(selector);
        element.clear();
    }

    public void focus() {
        WebElement element = driver.findElement(selector);
        element.click();
    }
}
