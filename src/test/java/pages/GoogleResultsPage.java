package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GoogleResultsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public GoogleResultsPage(WebDriver driver) {
        this.driver = driver;
        // Increased timeout for robustness
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));  // 60 seconds timeout for slower responses
    }

    // =========================
    // ROBUST WAIT FOR RESULTS
    // =========================
    public void waitForResultsPage() {
        try {
            wait.until(driver -> {
                // Ensure the page URL contains '/search' and that at least some search results are present
                boolean isOnSearchPage = driver.getCurrentUrl().contains("/search");
                boolean hasResults = !driver.findElements(By.cssSelector("div#rso")).isEmpty() || !driver.findElements(By.cssSelector("h3")).isEmpty();
                boolean hasResultsLinks = !driver.findElements(By.cssSelector("div#rso a")).isEmpty();  // Ensure there are anchor links in the results
                boolean isPageLoaded = isOnSearchPage && (hasResults || hasResultsLinks);

                // Log the page status
                Reporter.log("Page URL: " + driver.getCurrentUrl(), true);
                Reporter.log("Is on search page: " + isOnSearchPage, true);
                Reporter.log("Has results container: " + hasResults, true);
                Reporter.log("Has result links: " + hasResultsLinks, true);

                return isPageLoaded;
            });
        } catch (TimeoutException e) {
            Reporter.log("Timeout occurred while waiting for results page to load.", true);
            throw e; // Re-throw to indicate test failure
        }
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

        // Log the collected results for debugging
        Reporter.log("Top " + results.size() + " search results:", true);
        for (String result : results) {
            Reporter.log(result, true);
        }

        return results;
    }
}
