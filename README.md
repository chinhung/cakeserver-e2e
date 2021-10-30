# Cake Server End-to-End Testing

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/chinhung/pointwave/blob/master/LICENSE)

Cake Server end-to-end testing using Selenium and Cucumber.

## Introduction

[Cake Server](https://github.com/chinhung/cakeserver) is one of my open source projects which helps programmers to split their tasks. It is a web app and could be validated by end-to-end testing. This repository uses Selenium, Cucumber and Page Object pattern to perform the end-to-end testing.

## Chrome Driver Setup

This repository use chrome driver to manipulate Chrome browser. Because the version of the driver upgrades quickly, the driver is set to be git ignored. Please download the driver with the version number as the same with that of your Chrome browser, and put it in the folder under this project folder: 
```
./webdriver/chromedriver
```

The download link is [here](https://chromedriver.chromium.org/downloads).

## Run Tests
```
./gradlew cucumber
```

## Page Object

Page Object is an object which contains all the web elements as its member variables and encapsulate the operation details on a particular page:
```java
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

    // ...
}
```

Initialize the web elements:
```java
public class CakeServerPage {

    // ...

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

    // ...
}
```
Encapsulate the operation details:
```java
public class CakeServerPage {
    
    // ...

    public void open() {
        driver.get(url);
    }

    public void createNewCommit(String message, String note) {
        newCommitBtn.click();
        messageField.fill(message);
        noteField.fill(note);
        saveBtn.click();
    }

    public boolean commitExists(String message) {
        return commitList.commitExists(message);
    }
}
```

## Web Elements

Reusable web elements designed with DI principle:

### Button
```java
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
```

### Text Field
```java
public class TextField {

    private WebDriver driver;
    private By selector;

    public TextField(WebDriver driver, By selector) {
        this.driver = driver;
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
```

## Step Definitions

By default, cucumber creates new step definition instances for every step. In order to share states (variables, page objects ...etc.) between steps, we need to introduce DI container `guice` and annotate the step definition classes with `@ScenarioScoped` annotation.

```groovy
dependencies {
    testImplementation("io.cucumber:cucumber-guice:6.0.0")
    testImplementation("com.google.inject:guice:4.0")
}
```

```java
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
```

## Test Cases

```gherkin
Feature: Create Commit

    Scenario: create commit successfully
        Given commit does not exist: New Message
        When create commit with message: New Message and note: New Note
        Then commit exists: New Message
```