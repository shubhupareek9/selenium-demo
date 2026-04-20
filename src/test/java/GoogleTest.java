import io.qameta.allure.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import static org.junit.jupiter.api.Assertions.*;

@Epic("UI Automation")
@Feature("Basic Tests")
public class GoogleTest {

    private WebDriver getDriver() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        return new ChromeDriver(options);
    }

    @Test
    @Story("Open example site")
    @Severity(SeverityLevel.CRITICAL)
    public void openTest() {

        WebDriver driver = getDriver();

        driver.get("https://example.com");

        assertTrue(driver.getTitle().contains("Example"));

        driver.quit();
    }

    @Test
    @Story("Second test")
    public void secondTest() {

        WebDriver driver = getDriver();

        driver.get("https://example.com");

        assertNotNull(driver.getTitle());

        driver.quit();
    }
}
