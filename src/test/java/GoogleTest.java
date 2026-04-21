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
    }

    @Test
    public void openGoogleTest() {
        test = extent.createTest("Open Google Test");

        driver.get("https://www.google.com");

        test.info("Opened Google");

        assertTrue(driver.getTitle().toLowerCase().contains("google"));

        test.pass("Google title verified");
    }

    @Test
    public void searchTest() {
        test = extent.createTest("Search Test");

        driver.get("https://www.google.com");

        driver.findElement(By.name("q")).sendKeys("selenium webdriver");
        driver.findElement(By.name("q")).submit();

        test.info("Search executed");

        assertTrue(driver.getTitle().length() > 0);

        test.pass("Search successful");
    }

    // 🔥 NEW TESTS BELOW

    @Test
    public void verifySearchBoxPresent() {
        test = extent.createTest("Verify Search Box Present");

        driver.get("https://www.google.com");

        WebElement searchBox = driver.findElement(By.name("q"));

        assertTrue(searchBox.isDisplayed());

        test.pass("Search box is visible");
    }

    @Test
    public void verifyGoogleLogoPresent() {
        test = extent.createTest("Verify Google Logo");

        driver.get("https://www.google.com");

        WebElement logo = driver.findElement(By.xpath("//img[@alt='Google']"));

        assertTrue(logo.isDisplayed());

        test.pass("Google logo is visible");
    }

    @Test
    public void searchDifferentKeyword() {
        test = extent.createTest("Search Different Keyword");

        driver.get("https://www.google.com");

        driver.findElement(By.name("q")).sendKeys("automation testing");
        driver.findElement(By.name("q")).submit();

        assertTrue(driver.getTitle().toLowerCase().contains("automation"));

        test.pass("Search worked for different keyword");
    }

    @Test
    public void verifyTitleNotEmpty() {
        test = extent.createTest("Verify Title Not Empty");

        driver.get("https://www.google.com");

        assertFalse(driver.getTitle().isEmpty());

        test.pass("Title is not empty");
    }

    @Test
    public void verifyPageSourceContainsGoogle() {
        test = extent.createTest("Verify Page Source");

        driver.get("https://www.google.com");

        assertTrue(driver.getPageSource().toLowerCase().contains("google"));

        test.pass("Page source contains Google");
    }

    @Test
    public void verifySearchBoxEnabled() {
        test = extent.createTest("Verify Search Box Enabled");

        driver.get("https://www.google.com");

        WebElement searchBox = driver.findElement(By.name("q"));

        assertTrue(searchBox.isEnabled());

        test.pass("Search box is enabled");
    }

    @Test
    public void searchAndCheckResultsPage() {
        test = extent.createTest("Search and Check Results Page");

        driver.get("https://www.google.com");

        driver.findElement(By.name("q")).sendKeys("Selenium");
        driver.findElement(By.name("q")).submit();

        assertTrue(driver.getCurrentUrl().contains("search"));

        test.pass("Navigated to results page");
    }

    @Test
    public void verifyGoogleUrl() {
        test = extent.createTest("Verify Google URL");

        driver.get("https://www.google.com");

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
