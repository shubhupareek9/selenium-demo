import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.Test;

public class GoogleTest {

    @Test
    public void openGoogle() {

        // Launch Chrome browser
        WebDriver driver = new ChromeDriver();

        // Open Google
        driver.get("https://www.google.com");

        // Print page title
        System.out.println("Page Title: " + driver.getTitle());

        // Close browser
        driver.quit();
    }
}
