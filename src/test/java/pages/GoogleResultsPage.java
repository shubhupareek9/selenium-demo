package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GoogleResultsPage {

    private WebDriver driver;

    public GoogleResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    // Stable result page indicators (Google changes DOM often → use fallback)
    private By resultsRoot = By.cssSelector("div#search, div#rso");

    private By topNavItems = By.xpath("//div[@role='navigation']//a");

    private By toolsButton = By.xpath("//div[text()='Tools' or @aria-label='Tools']");

    // =========================
    // SAFE WAIT METHOD
    // =========================
    public void waitForResultsPage() {

        for (int i = 0; i < 20; i++) { // simple retry loop instead of flaky wait
            if (driver.findElements(resultsRoot).size() > 0) {
                return;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }

        throw new RuntimeException("Google results page not loaded properly");
    }

    // =========================
    // TABS
    // =========================
    public boolean isTabPresent(String tabName) {

        List<WebElement> tabs = driver.findElements(topNavItems);

        return tabs.stream()
                .anyMatch(t -> t.getText().toLowerCase().contains(tabName.toLowerCase()));
    }

    public void clickTab(String tabName) {

        List<WebElement> tabs = driver.findElements(topNavItems);

        for (WebElement tab : tabs) {
            String text = tab.getText().toLowerCase();

            if (text.contains(tabName.toLowerCase())) {
                tab.click();
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
        driver.findElement(toolsButton).click();
    }
}
