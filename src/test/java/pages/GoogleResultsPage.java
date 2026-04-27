package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class GoogleResultsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public GoogleResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Top navigation container
    private By topNavBar = By.xpath("//div[@role='navigation']");

    // All top menu items (All, Shopping, Videos, etc.)
    private By topMenuItems = By.xpath("//div[@role='navigation']//a");

    // Tools button (not always <a>)
    private By toolsButton = By.xpath("//div[text()='Tools' or @aria-label='Tools']");

    // ===== Utility Methods =====

    private List<WebElement> getMenuElements() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(topNavBar));
        return driver.findElements(topMenuItems);
    }

    // Get all visible menu text
    public List<String> getTopMenuOptions() {
        return getMenuElements()
                .stream()
                .map(e -> e.getText().trim())
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());
    }

    // Check if a tab exists (flexible match)
    public boolean isTabPresent(String tabName) {
        return getTopMenuOptions()
                .stream()
                .anyMatch(tab -> tab.toLowerCase().contains(tabName.toLowerCase()));
    }

    // Click tab dynamically (works for Shopping, Videos, Images, etc.)
    public void clickTab(String tabName) {
        List<WebElement> tabs = getMenuElements();

        for (WebElement tab : tabs) {
            String text = tab.getText().trim();
            if (text.equalsIgnoreCase(tabName) || text.toLowerCase().contains(tabName.toLowerCase())) {
                wait.until(ExpectedConditions.elementToBeClickable(tab)).click();
                return;
            }
        }

        throw new RuntimeException("Tab not found: " + tabName);
    }

    // Tools visibility
    public boolean isToolsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(toolsButton));
            return driver.findElement(toolsButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Click Tools
    public void clickTools() {
        wait.until(ExpectedConditions.elementToBeClickable(toolsButton)).click();
    }

    // Verify page loaded (basic check)
    public boolean isResultsPageLoaded() {
        return driver.getTitle().toLowerCase().contains("milk") ||
               driver.getCurrentUrl().contains("search");
    }
}
