package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class GoogleResultsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public GoogleResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // More stable than role=navigation
    private By resultsContainer = By.id("search");

    private By topMenuItems = By.xpath("//div[@role='navigation']//a");

    private By toolsButton = By.xpath("//div[text()='Tools' or @aria-label='Tools']");

    // =========================
    // WAIT FOR RESULTS PAGE
    // =========================
    public boolean waitForResultsPage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(resultsContainer));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // MENU ELEMENTS
    // =========================
    private List<WebElement> getMenuElements() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(resultsContainer));
        return driver.findElements(topMenuItems);
    }

    public List<String> getTopMenuOptions() {
        return getMenuElements()
                .stream()
                .map(e -> e.getText().trim())
                .filter(t -> !t.isEmpty())
                .collect(Collectors.toList());
    }

    public boolean isTabPresent(String tabName) {
        return getTopMenuOptions()
                .stream()
                .anyMatch(tab -> tab.toLowerCase().contains(tabName.toLowerCase()));
    }

    public void clickTab(String tabName) {
        List<WebElement> tabs = getMenuElements();

        for (WebElement tab : tabs) {
            String text = tab.getText().trim();

            if (text.equalsIgnoreCase(tabName) ||
                text.toLowerCase().contains(tabName.toLowerCase())) {

                wait.until(ExpectedConditions.elementToBeClickable(tab)).click();
                return;
            }
        }

        throw new RuntimeException("Tab not found: " + tabName);
    }

    // =========================
    // TOOLS BUTTON
    // =========================
    public boolean isToolsDisplayed() {
        try {
            return driver.findElement(toolsButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickTools() {
        wait.until(ExpectedConditions.elementToBeClickable(toolsButton)).click();
    }
}
