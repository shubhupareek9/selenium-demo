import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

import com.aventstack.extentreports.*;
import utils.ExtentManager;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GoogleTest {

    static ExtentReports extent;
    ExtentTest test;
    WebDriver driver;
    WebDriverWait wait;

    @BeforeAll
    public static void setupReport() {
        extent = ExtentManager.getExtent();
    }

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // 🔧 helper for clean step logging
    private void step(String message) {
        test.info(message);
    }

    private void openGoogle() {
        driver.get("https://www.google.com");

        wait.until(d ->
            ((JavascriptExecutor)d).executeScript("return document.readyState")
                .equals("complete")
        );
    }

    private WebElement getSearchBox() {
        return wait.until(
            ExpectedConditions.presenceOfElementLocated(By.name("q"))
        );
    }

    @Test
    public void openGoogleTest() {

        test = extent.createTest("Open Google Test");

        step("Opening Google homepage");
        openGoogle();

        step("Verifying page title");
        assertTrue(driver.getTitle().toLowerCase().contains("google"));

        test.pass("Google opened successfully");
    }

    @Test
    public void searchTest() {

        test = extent.createTest("Search Test");

        step("Opening Google");
        openGoogle();

        step("Locating search box");
        WebElement box = getSearchBox();

        step("Entering search text: selenium");
        box.sendKeys("selenium");

        step("Submitting search");
        box.submit();

        step("Waiting for results page");
        wait.until(ExpectedConditions.titleContains("selenium"));

        step("Validating results title");
        assertTrue(driver.getTitle().toLowerCase().contains("selenium"));

        test.pass("Search test completed successfully");
    }

    @Test
    public void searchAutomation() {

        test = extent.createTest("Automation Search Test");

        step("Opening Google");
        openGoogle();

        step("Finding search box");
        WebElement box = getSearchBox();

        step("Typing automation testing");
        box.sendKeys("automation testing");

        step("Submitting search");
        box.submit();

        step("Waiting for page title");
        wait.until(ExpectedConditions.titleContains("automation"));

        step("Validating result");
        assertTrue(driver.getTitle().toLowerCase().contains("automation"));

        test.pass("Automation search successful");
    }

    @Test
    public void verifySearchBoxPresent() {

        test = extent.createTest("Search Box Present");

        step("Opening Google");
        openGoogle();

        step("Checking search box visibility");
        assertTrue(getSearchBox().isDisplayed());

        test.pass("Search box is visible");
    }

    @Test
    public void verifySearchBoxEnabled() {

        test = extent.createTest("Search Box Enabled");

        step("Opening Google");
        openGoogle();

        step("Checking if search box is enabled");
        assertTrue(getSearchBox().isEnabled());

        test.pass("Search box is enabled");
    }

    @Test
    public void verifyTitleNotEmpty() {

        test = extent.createTest("Title Not Empty");

        step("Opening Google");
        openGoogle();

        step("Checking page title");
        assertFalse(driver.getTitle().isEmpty());

        test.pass("Title is not empty");
    }

    @Test
    public void verifyGoogleInTitle() {

        test = extent.createTest("Title Contains Google");

        step("Opening Google");
        openGoogle();

        step("Validating title contains 'google'");
        assertTrue(driver.getTitle().toLowerCase().contains("google"));

        test.pass("Title verified");
    }

    @Test
    public void verifyPageLoads() {

        test = extent.createTest("Page Load Test");

        step("Opening Google");
        openGoogle();

        step("Checking document ready state");

        String state = (String)((JavascriptExecutor)driver)
                .executeScript("return document.readyState");

        assertEquals("complete", state);

        test.pass("Page loaded successfully");
    }

    @Test
    public void verifyUrl() {

        test = extent.createTest("URL Test");

        step("Opening Google");
        openGoogle();

        step("Checking current URL");
        assertTrue(driver.getCurrentUrl().contains("google"));

        test.pass("URL is correct");
    }

    @Test
    public void verifySearchSuggestionsAppear() {

        test = extent.createTest("Search Suggestions Test");

        step("Opening Google");
        openGoogle();

        step("Typing search text");
        WebElement box = getSearchBox();
        box.sendKeys("selenium");

        step("Waiting for suggestions dropdown");

        List<WebElement> suggestions = wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//ul[@role='listbox']//li")
            )
        );

        step("Validating suggestions count");
        assertTrue(suggestions.size() > 0);

        test.pass("Suggestions appeared successfully");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @AfterAll
    public static void flushReport() {
        extent.flush();
    }
}
