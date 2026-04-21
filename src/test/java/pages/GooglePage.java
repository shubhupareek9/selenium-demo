package pages;

import org.openqa.selenium.*;

public class GooglePage {

    WebDriver driver;

    private By searchBox = By.name("q");

    public GooglePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://www.google.com");
    }

    public void search(String text) {
        driver.findElement(searchBox).sendKeys(text);
        driver.findElement(searchBox).submit();
    }

    public boolean isSearchBoxDisplayed() {
        return driver.findElement(searchBox).isDisplayed();
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
