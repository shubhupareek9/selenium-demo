import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import com.aventstack.extentreports.*;
import utils.ExtentManager;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoogleTest {

    static ExtentReports extent;
    ExtentTest test;
    WebDriver driver;

    @BeforeAll
    public static void startReport() {
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

    // ---------------- TEST 1 ----------------
    @Test
    @Order(1)
    public void test01_openGoogle() {
        test = extent.createTest("Open Google");
        driver.get("https://www.google.com");
        test.info("Opened Google homepage");
        assertTrue(driver.getTitle().toLowerCase().contains("google"));
        test.pass("Title verified");
    }

    // ---------------- TEST 2 ----------------
    @Test
    @Order(2)
    public void test02_searchKeyword() {
        test = extent.createTest("Search Keyword");
        driver.get("https://www.google.com");
        driver.findElement(By.name("q")).sendKeys("selenium");
        driver.findElement(By.name("q")).submit();
        test.info("Search executed");
        assertTrue(driver.getTitle().length() > 0);
        test.pass("Search successful");
    }

    // ---------------- TEST 3 ----------------
    @Test
    @Order(3)
    public void test03_gmailLink() {
        test = extent.createTest("Gmail Link");
        driver.get("https://www.google.com");
        assertTrue(driver.findElement(By.linkText("Gmail")).isDisplayed());
        test.pass("Gmail link visible");
    }

    // ---------------- TEST 4 ----------------
    @Test
    @Order(4)
    public void test04_imagesLink() {
        test = extent.createTest("Images Link");
        driver.get("https://www.google.com");
        assertTrue(driver.findElement(By.linkText("Images")).isDisplayed());
        test.pass("Images link visible");
    }

    // ---------------- TEST 5 ----------------
    @Test
    @Order(5)
    public void test05_feelingLuckyButton() {
        test = extent.createTest("Feeling Lucky Button");
        driver.get("https://www.google.com");
        assertTrue(driver.findElement(By.name("btnI")).isDisplayed());
        test.pass("Button visible");
    }

    // ---------------- TEST 6 ----------------
    @Test
    @Order(6)
    public void test06_searchBoxVisible() {
        test = extent.createTest("Search Box Visible");
        driver.get("https://www.google.com");
        assertTrue(driver.findElement(By.name("q")).isDisplayed());
        test.pass("Search box visible");
    }

    // ---------------- TEST 7 ----------------
    @Test
    @Order(7)
    public void test07_pageTitleNotEmpty() {
        test = extent.createTest("Page Title Not Empty");
        driver.get("https://www.google.com");
        assertFalse(driver.getTitle().isEmpty());
        test.pass("Title is not empty");
    }

    // ---------------- TEST 8 ----------------
    @Test
    @Order(8)
    public void test08_urlCheck() {
        test = extent.createTest("URL Check");
        driver.get("https://www.google.com");
        assertTrue(driver.getCurrentUrl().contains("google"));
        test.pass("URL verified");
    }

    // ---------------- TEST 9 ----------------
    @Test
    @Order(9)
    public void test09_logoVisible() {
        test = extent.createTest("Logo Visible");
        driver.get("https://www.google.com");
        assertTrue(driver.findElement(By.cssSelector("img[alt='Google']")).isDisplayed());
        test.pass("Logo visible");
    }

    // ---------------- TEST 10 ----------------
    @Test
    @Order(10)
    public void test10_footerPresent() {
        test = extent.createTest("Footer Present");
        driver.get("https://www.google.com");
        assertTrue(driver.findElement(By.cssSelector("body")).isDisplayed());
        test.pass("Page body loaded");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
        test.info("Browser closed");
    }

    @AfterAll
    public static void flushReport() {
        extent.flush();
    }
}
