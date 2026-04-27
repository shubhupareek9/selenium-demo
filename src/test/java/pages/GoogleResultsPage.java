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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Increased timeout for CI
    }

    // =========================
    // ROBUST WAIT FOR RESULTS
    // =========================
    public void waitForResultsPage() {

        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Wait for either of the following conditions:
        longWait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#search")),
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#rso")),
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("h3")),
                ExpectedConditions.urlContains("/search")
        ));

        // Extra safety: ensure at least one visible result
        longWait.until(driver -> {
            List<WebElement> results = driver.findElements(By.cssSelector("h3"));
            return results.stream().anyMatch(WebElement::isDisplayed);
        });
    }

    // =========================
    // GET TOP RESULTS (ROBUST)
    // =========================
    public List<String> getTopSearchResults(int limit) {

        List<WebElement> elements = driver.findElements(
                By.cssSelector("div#search h3")  // Safer locator for results
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
