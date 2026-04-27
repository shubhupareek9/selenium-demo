package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleResultsPage {

    private WebDriver driver;

    public GoogleResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    // Stable result indicators (Google varies DOM, so use multiple fallbacks)
    private By searchResults = By.id("search");
    private By resultsRoot = By.id("rso");

    private By topNavItems = By.xpath("//div[@role='navigation']//a");

    private By toolsButton = By.xpath("//div[text()='Tools' or @aria-label='Tools']");

    // =========================
    // FIXED: BOOLEAN WAIT METHOD
    // =========================
    public boolean waitForResultsPage() {

        for (int i = 0; i < 20; i++) {

            if (driver.findElements(searchResults).size() > 0 ||
                driver.findElements(resultsRoot).size() > 0) {
                return true;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }

        return false;
    }

    // =========================
    // TABS
    // =========================
    public boolean isTabPresent(String tabName) {

        return driver.findElements(topNavItems)
                .stream()
                .anyMatch(e -> e.getText().toLowerCase().contains(tabName.toLowerCase()));
    }

    public void clickTab(String tabName) {

        driver.findElements(topNavItems)
                .stream()
                .filter(e -> e.getText().toLowerCase().contains(tabName.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tab not found: " + tabName))
                .click();
    }

    // =========================
    // TOOLS
    // =========================
    public boolean isToolsDisplayed() {
        return driver.findElements(toolsButton).size() > 0;
    }

    public void clickTools() {
        driver.findElement(toolsButton).click();
    }
}
