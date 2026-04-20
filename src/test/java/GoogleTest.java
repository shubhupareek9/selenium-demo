import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;

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
    public void openGoogleTest() {
        WebDriver driver = getDriver();

        driver.get("https://www.google.com");

        assertTrue(driver.getTitle().toLowerCase().contains("google"));

        driver.quit();
    }

    @Test
    public void searchInGoogleTest() {
        WebDriver driver = getDriver();

        driver.get("https://www.google.com");

        driver.findElement(By.name("q")).sendKeys("selenium webdriver");
        driver.findElement(By.name("q")).submit();

        assertTrue(driver.getTitle().toLowerCase().contains("selenium"));

        driver.quit();
    }

    @Test
    public void checkSearchBoxExistsTest() {
        WebDriver driver = getDriver();

        driver.get("https://www.google.com");

        boolean isDisplayed = driver.findElement(By.name("q")).isDisplayed();

        assertTrue(isDisplayed);

        driver.quit();
    }
}
