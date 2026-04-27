package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GoogleResultsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public GoogleResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    // =========================
    // LOCATORS (ROBUST)
    // =========================
    private By searchResults = By.id("search");
    private By resultsRoot = By.id("rso");

    private By navTabs = By.xpath("//div[@role='navigation']//a | //div[@role='tab']");

    private By toolsButton = By.xpath("//*[contains(text(),'Tools')]");

    // =========================
    // WAIT FOR RESULTS PAGE
    // =========================
    public boolean waitForResultsPage() {
        try {
            wait.until(driver ->
                    driver.findElements(searchResults).size() > 0 ||
                    driver.findElements(resultsRoot).size() > 0
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // TAB VALIDATION
    // =========================
    public boolean isTabPresent(String tabName) {

        List<WebElement> tabs = driver.findElements(navTabs);

        for (WebElement tab : tabs) {

            String text = tab.getText();

            if (text != null &&
                    text.trim().toLowerCase().contains(tabName.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    // =========================
    // TAB CLICK
    // =========================
    public void clickTab(String tabName) {

        List<WebElement> tabs = driver.findElements(navTabs);

        for (WebElement tab : tabs) {

            String text = tab.getText();

            if (text != null &&
                    text.trim().toLowerCase().contains(tabName.toLowerCase())) {

                wait.until(ExpectedConditions.elementToBeClickable(tab)).click();
                return;
            }
        }

        throw new RuntimeException("Tab not found: " + tabName);
    }

    // =========================
    // TOOLS
    // =========================
    public boolean isToolsDisplayed() {

        return driver.findElements(toolsButton).size() > 0;
    }

    public void clickTools() {

        WebElement tools = driver.findElement(toolsButton);
        wait.until(ExpectedConditions.elementToBeClickable(tools)).click();
    }

    // =========================
    // FLOW METHOD (REUSABLE)
    // =========================
    public void openSearchResults(WebDriver driver, String term, GooglePage googlePage) {

        googlePage.open();
        googlePage.search(term);
        waitForResultsPage();
    }
}
