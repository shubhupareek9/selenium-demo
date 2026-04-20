import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Google Automation")
@Feature("Search Feature")

public class GoogleTest {

    private WebDriver getDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }

    @Test
    @Story("Open Google Home Page")
    @Severity(SeverityLevel.CRITICAL)
    public void openGoogleTest() {

        WebDriver driver = getDriver();

        driver.get("https://www.google.com");

        assertTrue(driver.getTitle().toLowerCase().contains("google"));

        driver.quit();
    }

    @Test
    @Story("Search in Google")
    @Severity(SeverityLevel.NORMAL)
    public void searchTest() {

        WebDriver driver = getDriver();

        driver.get("https://www.google.com");

        driver.findElement(By.name("q")).sendKeys("selenium webdriver");
        driver.findElement(By.name("q")).submit();

        assertTrue(driver.getTitle().toLowerCase().contains("selenium"));

        driver.quit();
    }

    @Test
    @Story("Check search box exists")
    @Severity(SeverityLevel.MINOR)
    public void searchBoxTest() {

        WebDriver driver = getDriver();

        driver.get("https://www.google.com");

        assertTrue(driver.findElement(By.name("q")).isDisplayed());

        driver.quit();
    }
}
