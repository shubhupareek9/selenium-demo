import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import com.aventstack.extentreports.*;
import utils.ExtentManager;

import static org.junit.jupiter.api.Assertions.*;

public class GoogleTest {

    static ExtentReports extent;
    ExtentTest test;
    WebDriver driver;

    // ---------------- REPORT INIT ----------------
    @BeforeAll
    public static void setupReport() {
        extent = ExtentManager.getExtent();
    }

    // ---------------- SETUP ----------------
    @BeforeEach
    public void setup(TestInfo testInfo) {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);

        // AUTO CREATE TEST ENTRY
        test = extent.createTest(testInfo.getDisplayName());
        test.info("Browser started");
    }

    // ---------------- TEST 1 ----------------
    @Test
    @DisplayName("Open Google Homepage")
    public void openGoogle() {

        test.info("Navigating to Google");

        driver.get("https://www.google.com");

        test.info("Page loaded");

        String title = driver.getTitle();
        test.info("Page title: " + title);

        assertTrue(title.toLowerCase().contains("google"));

        test.pass("Google homepage validated");
    }

    // ---------------- TEST 2 ----------------
    @Test
    @DisplayName("Google Search Functionality")
    public void searchGoogle() {

        test.info("Opening Google");

        driver.get("https://www.google.com");

        test.info("Entering search text");

        WebElement box = driver.findElement(By.name("q"));
        box.sendKeys("selenium webdriver");

        test.info("Submitting search");
        box.submit();

        test.info("Validating results page");

        assertTrue(driver.getTitle().length() > 0);

        test.pass("Search executed successfully");
    }

    // ---------------- TEST 3 ----------------
    @Test
    @DisplayName("Verify Gmail Link")
    public void gmailLinkTest() {

        test.info("Opening Google");

        driver.get("https://www.google.com");

        test.info("Checking Gmail link presence");

        WebElement gmail = driver.findElement(By.linkText("Gmail"));

        assertTrue(gmail.isDisplayed());

        test.pass("Gmail link is visible");
    }

    // ---------------- CLEANUP ----------------
    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }

        test.info("Browser closed");
    }

    // ---------------- FLUSH REPORT ----------------
    @AfterAll
    public static void flushReport() {
        extent.flush();
    }
}
