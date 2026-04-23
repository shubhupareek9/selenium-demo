ackage pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GooglePage {

    WebDriver driver;

    // Locator
    private By searchBox = By.name("q");

    // Constructor
    public GooglePage(WebDriver driver) {
        this.driver = driver;
    }

    // Open Google
    public void open() {
        driver.get("https://www.google.com");
    }

    // Perform search
    public void search(String text) {
        driver.findElement(searchBox).sendKeys(text);
        driver.findElement(searchBox).submit();
    }

    // Check search box visibility
    public boolean isSearchBoxDisplayed() {
        return driver.findElement(searchBox).isDisplayed();
    }

    // Get page title
    public String getTitle() {
        return driver.getTitle();
    }

    // ✅ New method for your "milk" test
    public boolean isOnResultsPage() {
        return driver.getCurrentUrl().contains("search");
    }
}
