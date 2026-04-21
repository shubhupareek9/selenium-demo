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
        test = extent.createTest("Open Google");

        openGoogle();

        assertTrue(driver.getTitle().toLowerCase().contains("google"));

        test.pass("Google opened successfully");
    }

    @Test
    public void searchTest() {
        test = extent.createTest("Search Selenium");

        openGoogle();

        WebElement box = getSearchBox();
        box.sendKeys("selenium");
        box.submit();

        wait.until(ExpectedConditions.titleContains("selenium"));

        assertTrue(driver.getTitle().toLowerCase().contains("selenium"));

        test.pass("Search worked");
    }

    @Test
    public void searchAutomation() {
        test = extent.createTest("Search Automation");

        openGoogle();

        WebElement box = getSearchBox();
        box.sendKeys("automation testing");
        box.submit();

        wait.until(ExpectedConditions.titleContains("automation"));

        assertTrue(driver.getTitle().toLowerCase().contains("automation"));

        test.pass("Automation search passed");
    }

    @Test
    public void verifySearchBoxPresent() {
        test = extent.createTest("Search Box Present");

        openGoogle();

        assertTrue(getSearchBox().isDisplayed());

        test.pass("Search box visible");
    }

    @Test
    public void verifySearchBoxEnabled() {
        test = extent.createTest("Search Box Enabled");

        openGoogle();

        assertTrue(getSearchBox().isEnabled());

        test.pass("Search box enabled");
    }

    @Test
    public void verifyTitleNotEmpty() {
        test = extent.createTest("Title Not Empty");

        openGoogle();

        assertFalse(driver.getTitle().isEmpty());

        test.pass("Title is not empty");
    }

    @Test
    public void verifyGoogleInTitle() {
        test = extent.createTest("Title Contains Google");

        openGoogle();

        assertTrue(driver.getTitle().toLowerCase().contains("google"));

        test.pass("Title contains Google");
    }

    @Test
    public void verifyPageLoads() {
        test = extent.createTest("Page Load Test");

        openGoogle();

        String state = (String)((JavascriptExecutor)driver)
                .executeScript("return document.readyState");

        assertEquals("complete", state);

        test.pass("Page fully loaded");
    }

    @Test
    public void verifyUrl() {
        test = extent.createTest("URL Test");

        openGoogle();

        assertTrue(driver.getCurrentUrl().contains("google"));

        test.pass("Correct URL");
    }

    @Test
    public void verifySearchSuggestionsAppear() {
        test = extent.createTest("Search Suggestions Appear");

        openGoogle();

        WebElement box = getSearchBox();
        box.sendKeys("selenium");

        // wait for suggestions dropdown
        List<WebElement> suggestions = wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//ul[@role='listbox']//li")
            )
        );

        assertTrue(suggestions.size() > 0);

        test.pass("Search suggestions displayed");
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
