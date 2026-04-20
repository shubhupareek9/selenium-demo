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

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @AfterAll
    public static void flushReport() {
        extent.flush();
    }
}
