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
    // TAB VALIDATION (ROBUST)
    // =========================
    public boolean isTabPresent(String tabName) {

        try {
            // Wait until navigation bar is present
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(topNavItems));

            List<WebElement> tabs = driver.findElements(topNavItems);

            for (WebElement tab : tabs) {

                // Visible text
                String text = tab.getText().trim();

                if (!text.isEmpty() && text.equalsIgnoreCase(tabName)) {
                    return true;
                }

                // Fallback: aria-label (important for Google)
                String aria = tab.getAttribute("aria-label");
                if (aria != null && aria.equalsIgnoreCase(tabName)) {
                    return true;
                }
            }

        } catch (Exception ignored) {}

        return false;
    }

    // =========================
    // CLICK TAB (ROBUST)
    // =========================
    public void clickTab(String tabName) {

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(topNavItems));

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
            return wait.until(ExpectedConditions.visibilityOfElementLocated(toolsButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickTools() {
        wait.until(ExpectedConditions.elementToBeClickable(toolsButton)).click();
    }
}
