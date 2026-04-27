package pages;

import org.openqa.selenium.*;
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
    // CORE RESULTS CHECK
    // =========================
    public boolean waitForResultsPage() {
        try {
            Thread.sleep(2000); // small UI stabilization delay
            return driver.getTitle().toLowerCase().contains("google");
        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // STABLE TAB DETECTION
    // =========================
    public boolean isTabPresent(String tabName) {

        try {
            List<WebElement> tabs = driver.findElements(
                    By.xpath("//a[contains(@href,'tbm=') or @role='link']")
            );

            for (WebElement tab : tabs) {
                String text = tab.getText();
                if (text != null && text.trim().equalsIgnoreCase(tabName)) {
                    return true;
                }
            }

            return driver.getPageSource().toLowerCase().contains(tabName.toLowerCase());

        } catch (Exception e) {
            return false;
        }
    }

    // =========================
    // TAB CLICK (SAFE)
    // =========================
    public void clickTab(String tabName) {

        List<WebElement> tabs = driver.findElements(By.tagName("a"));

        for (WebElement tab : tabs) {

            try {
                String text = tab.getText();

                if (text != null && text.trim().equalsIgnoreCase(tabName)) {
                    tab.click();
                    return;
                }

            } catch (Exception ignored) {}
        }

        throw new RuntimeException("Tab not found: " + tabName);
    }

    // =========================
    // TOOLS BUTTON FIX
    // =========================
    public boolean isToolsDisplayed() {

        return driver.getPageSource().toLowerCase().contains("tools");
    }

    public void clickTools() {

        List<WebElement> elements = driver.findElements(By.tagName("div"));

        for (WebElement el : elements) {

            try {
                if (el.getText().equalsIgnoreCase("Tools")) {
                    el.click();
                    return;
                }
            } catch (Exception ignored) {}
        }
    }
}
