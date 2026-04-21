import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

import com.aventstack.extentreports.*;
import utils.ExtentManager;

import java.time.Duration;

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ✅ Handle cookie popup safely
    private void handleCookies() {
        try {
            WebElement accept = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Accept')]")
                )
            );
            accept.click();
        } catch (Exception e) {
            // ignore if not present
        }
    }

    @Test
    public void openGoogleTest() {
        test = extent.createTest("Open Google Test");

        driver.get("https://www.google.com");
        handleCookies();

        test.info("Opened Google");

        assertTrue(driver.getTitle().toLowerCase().contains("google"));

        test.pass("Google title verified");
    }

    @Test
    public void searchTest() {
        test = extent.createTest("Search Test");

        driver.get("https://www.google.com");
        handleCookies();

        WebElement searchBox = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.name("q"))
        );

        searchBox.sendKeys("selenium webdriver");
        searchBox.submit();

        test.info("Search executed");

        assertTrue(driver.getTitle().length() > 0);

        test.pass("Search successful");
    }

    @Test
    public void verifySearchBoxPresent() {
        test = extent.createTest("Verify Search Box Present");

        driver.get("https://www.google.com");
        handleCookies();

        WebElement searchBox = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.name("q"))
        );

        assertTrue(searchBox.isDisplayed());

        test.pass("Search box is visible");
    }

    @Test
    public void verifyGoogleLogoPresent() {
        test = extent.createTest("Verify Google Logo");

        driver.get("https://www.google.com");
        handleCookies();

        WebElement logo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//img[@alt='Google']")
            )
        );

        assertTrue(logo.isDisplayed());

        test.pass("Google logo is visible");
    }

    @Test
    public void searchDifferentKeyword() {
        test = extent.createTest("Search Different Keyword");

        driver.get("https://www.google.com");
        handleCookies();

        WebElement searchBox = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.name("q"))
        );

        searchBox.sendKeys("automation testing");
        searchBox.submit();

        assertTrue(driver.getTitle().toLowerCase().contains("automation"));

        test.pass("Search worked for different keyword");
    }

    @Test
    public void verifyTitleNotEmpty() {
        test = extent.createTest("Verify Title Not Empty");

        driver.get("https://www.google.com");
        handleCookies();

        assertFalse(driver.getTitle().isEmpty());

        test.pass("Title is not empty");
    }

    @Test
    public void verifyPageSourceContainsGoogle() {
        test = extent.createTest("Verify Page Source");

        driver.get("https://www.google.com");
        handleCookies();

        assertTrue(driver.getPageSource().toLowerCase().contains("google"));

        test.pass("Page source contains Google");
    }

    @Test
    public void verifySearchBoxEnabled() {
        test = extent.createTest("Verify Search Box Enabled");

        driver.get("https://www.google.com");
        handleCookies();

        WebElement searchBox = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.name("q"))
        );

        assertTrue(searchBox.isEnabled());

        test.pass("Search box is enabled");
    }

    @Test
    public void searchAndCheckResultsPage() {
        test = extent.createTest("Search and Check Results Page");

        driver.get("https://www.google.com");
        handleCookies();

        WebElement searchBox = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.name("q"))
        );

        searchBox.sendKeys("Selenium");
        searchBox.submit();

        wait.until(ExpectedConditions.urlContains("search"));

        assertTrue(driver.getCurrentUrl().contains("search"));

        test.pass("Navigated to results page");
    }

    @Test
    public void verifyGoogleUrl() {
        test = extent.createTest("Verify Google URL");

        driver.get("https://www.google.com");
        handleCookies();

        assertTrue(driver.getCurrentUrl().contains("google"));

        test.pass("Correct URL loaded");
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
