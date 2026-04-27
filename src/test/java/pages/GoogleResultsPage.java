package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GoogleResultsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public GoogleResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // =========================
    // LOCATORS
    // =========================
    private By searchResults = By.id("search");
    private By resultsRoot = By.id("rso");
    private By topNavItems = By.cssSelector("div[role='navigation'] a");
    private By toolsButton = By.xpath("//div[text()='Tools' or @aria-label='Tools']");

    // =========================
    // WAIT
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
    // TAB METHODS
    // =========================
    public boolean isTabPresent(String tabName) {

        List<WebElement> tabs = driver.findElements(topNavItems);

        for (WebElement tab : tabs) {

            String text = tab.getText().trim();
            String aria = tab.getAttribute("aria-label");

            if ((text != null && text.equalsIgnoreCase(tabName)) ||
                (aria != null && aria.equalsIgnoreCase(tabName))) {
                return true;
            }
        }

        return false;
    }

    public void clickTab(String tabName) {

        List<WebElement> tabs = driver.findElements(topNavItems);

        for (WebElement tab : tabs) {

            String text = tab.getText().trim();
            String aria = tab.getAttribute("aria-label");

            if ((text != null && text.equalsIgnoreCase(tabName)) ||
                (aria != null && aria.equalsIgnoreCase(tabName))) {

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
        try {
            return driver.findElement(toolsButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickTools() {
        driver.findElement(toolsButton).click();
    }

    // =========================
    // FLOW METHOD (USED BY TESTS)
    // =========================
    public void openSearchResults(String term, GooglePage googlePage) {
        googlePage.open();
        googlePage.search(term);
        waitForResultsPage();
    }
}
