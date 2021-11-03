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
Delegate the operations to elements:
```java
public class CakeServerPage {
    
    // ...

    public void open() {
        driver.get(url);
    }

    public boolean commitExists(String message) {
        return commitList.commitExists(message);
    }

    public boolean commitExists(String message, String note) {
        return commitList.commitExists(message, note);
    }

    public void selectCommitWithMessageAndNote(String message, String note) {
        commitList.selectCommitWithMessageAndNote(message, note);
    }

    public void enterCommitMessage(String message) {
        messageField.fill(message);
    }

    public void enterCommitNote(String note) {
        noteField.fill(note);
    }

    public void saveCommit() {
        saveBtn.click();
    }

    public void selectNewCommit() {
        newCommitBtn.click();
    }
}
```

## Web Elements

Reusable web elements designed with DI principle, and which encapsulate the operation details:

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
        element.clear();
        element.sendKeys(text);
    }

    public String grab() {
        WebElement element = driver.findElement(selector);
        return element.getAttribute("value");
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
        cakeServerPage.selectNewCommit();
        cakeServerPage.enterCommitMessage(message);
        cakeServerPage.enterCommitNote(note);
        cakeServerPage.saveCommit();
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
```

## Test Cases

```gherkin
Feature: Create Commit

    Scenario: create commit successfully
        Given commit with message: New Message does not exist
        When create commit with message: New Message and note: New Note
        Then commit with message: New Message and note: New Note exists
```

```gherkin
Feature: Update Commit

    Background:
        Given create commit with message: Given Message and note: Given Note

    Scenario: update commit message
        When select commit with message: Given Message and note: Given Note
        * enter commit message: Updated
        * save commit
        Then commit with message: Updated and note: Given Note exists

    Scenario: update commit note
        When select commit with message: Given Message and note: Given Note
        * enter commit note: Updated
        * save commit
        Then commit with message: Given Message and note: Updated exists
```

See all test cases at `./src/test/resources/features`