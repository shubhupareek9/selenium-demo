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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // =========================
    // WAIT FOR RESULTS PAGE
    // =========================
    public void waitForResultsPage() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div#search")
        ));
    }

    // =========================
    // GET TOP SEARCH RESULTS
    // =========================
    public List<String> getTopSearchResults(int limit) {

        List<WebElement> elements = driver.findElements(
                By.cssSelector("div#search h3")
        );

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
