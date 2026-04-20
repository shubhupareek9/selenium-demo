import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class GoogleTest {

    private WebDriver getDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }

    @Test
    @Epic("Google Tests")
    @Feature("Search")
    @Story("Open Google")
    public void openGoogle() {

        WebDriver driver = getDriver();

        driver.get("https://www.google.com");

        assertTrue(driver.getTitle().toLowerCase().contains("google"));

        driver.quit();
    }

    @Test
    @Story("Search test")
    public void searchGoogle() {

        WebDriver driver = getDriver();

        driver.get("https://www.google.com");

        driver.findElement(By.name("q")).sendKeys("selenium");
        driver.findElement(By.name("q")).submit();

        assertTrue(driver.getTitle().toLowerCase().contains("selenium"));

        driver.quit();
    }
}
