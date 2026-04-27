package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GoogleResultsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public GoogleResultsPage(WebDriver driver) {
        this.driver = driver;
        // Increased timeout for robustness
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(45));  // 45 seconds timeout
    }

    // =========================
    // ROBUST WAIT FOR RESULTS
    // =========================
    public void waitForResultsPage() {
        wait.until(driver -> {
            try {
                // Checking for two things:
                // 1. If 'div#rso' is present (main search results container)
                // 2. If the URL contains '/search' (to ensure we are on the search results page)
                return driver.getCurrentUrl().contains("/search") &&
                       (driver.findElements(By.cssSelector("div#rso")).size() > 0
                        || driver.findElements(By.cssSelector("h3")).size() > 0);
            } catch (Exception e) {
                return false;
            }
        });
    }

    // =========================
    // GET TOP RESULTS (ROBUST)
    // =========================
    public List<String> getTopSearchResults(int limit) {

        List<WebElement> elements = driver.findElements(
                By.cssSelector("div#rso h3")
        );

        // fallback if structure changes
        if (elements.isEmpty()) {
            elements = driver.findElements(By.tagName("h3"));
        }

        List<String> results = new ArrayList<>();

        for (WebElement el : elements) {

            String text = el.getText().trim();

            if (!text.isEmpty()) {
                results.add(text);
            }

            if (results.size() == limit) {
                break;
            }
        }

        return results;
    }
}
