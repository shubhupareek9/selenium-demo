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

    private void step(String msg) {
        test.info(msg);
    }

    private void openGoogle() {
        driver.get("https://www.google.com");
        step("Opened Google homepage");
    }

    // 1
    @Test
    public void test01_openGoogle() {
        test = extent.createTest("Open Google");
        openGoogle();
        assertTrue(driver.getTitle().contains("Google"));
        test.pass("Title verified");
    }

    // 2
    @Test
    public void test02_searchSelenium() {
        test = extent.createTest("Search Selenium");

        openGoogle();

        WebElement box = wait.until(d -> d.findElement(By.name("q")));
        box.sendKeys("selenium webdriver");
        step("Entered search text");

        box.submit();
        step("Submitted search");

        wait.until(d -> d.getTitle().toLowerCase().contains("selenium"));

        assertTrue(driver.getTitle().toLowerCase().contains("selenium"));
        test.pass("Search successful");
    }

    // 3
    @Test
    public void test03_searchJava() {
        test = extent.createTest("Search Java");

        openGoogle();

        WebElement box = wait.until(d -> d.findElement(By.name("q")));
        box.sendKeys("java tutorials");
        step("Typed Java query");

        box.submit();

        wait.until(d -> d.getTitle().length() > 0);
        test.pass("Java search executed");
    }

    // 4
    @Test
    public void test04_searchSeleniumHQ() {
        test = extent.createTest("Search SeleniumHQ");

        openGoogle();

        WebElement box = wait.until(d -> d.findElement(By.name("q")));
        box.sendKeys("seleniumhq");
        box.submit();

        step("SeleniumHQ searched");
        test.pass("Search done");
    }

    // 5
    @Test
    public void test05_verifySearchBoxVisible() {
        test = extent.createTest("Search Box Visible");

        openGoogle();

        WebElement box = wait.until(d -> d.findElement(By.name("q")));
        assertTrue(box.isDisplayed());

        test.pass("Search box visible");
    }

    // 6
    @Test
    public void test06_clearSearchBox() {
        test = extent.createTest("Clear Search Box");

        openGoogle();

        WebElement box = wait.until(d -> d.findElement(By.name("q")));
        box.sendKeys("test data");
        box.clear();

        assertEquals("", box.getAttribute("value"));
        test.pass("Cleared successfully");
    }

    // 7
    @Test
    public void test07_searchSuggestionsAppear() {
        test = extent.createTest("Search Suggestions");

        openGoogle();

        WebElement box = wait.until(d -> d.findElement(By.name("q")));
        box.sendKeys("sel");

        wait.until(d -> d.findElements(By.cssSelector("ul li")).size() > 0);

        test.pass("Suggestions appeared");
    }

    // 8
    @Test
    public void test08_pageTitleNotEmpty() {
        test = extent.createTest("Title Not Empty");

        openGoogle();

        assertFalse(driver.getTitle().isEmpty());
        test.pass("Title is not empty");
    }

    // 9
    @Test
    public void test09_urlContainsGoogle() {
        test = extent.createTest("URL Check");

        openGoogle();

        assertTrue(driver.getCurrentUrl().contains("google"));
        test.pass("URL verified");
    }

    // 10
    @Test
    public void test10_inputAcceptsText() {
        test = extent.createTest("Input Accepts Text");

        openGoogle();

        WebElement box = wait.until(d -> d.findElement(By.name("q")));
        box.sendKeys("automation test");

        assertEquals("automation test", box.getAttribute("value"));
        test.pass("Input works correctly");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @AfterAll
    public static void flushReport() {
        extent.flush();
    }
}
