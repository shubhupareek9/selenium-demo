package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GooglePage {

    WebDriver driver;
    WebDriverWait wait;

    // ================= LOCATORS =================

    private By searchBox = By.name("q");

    private By googleLogo = By.cssSelector("img[alt*='Google'], svg");

    private By aboutLink = By.linkText("About");
    private By storeLink = By.linkText("Store");

    private By gmailLink = By.linkText("Gmail");
    private By imagesLink = By.linkText("Images");

    private By appsGrid = By.cssSelector("a[aria-label='Google apps']");

    private By googleSearchBtn = By.name("btnK");
    private By feelingLuckyBtn = By.name("btnI");

    // ================= CONSTRUCTOR =================

    public GooglePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ================= OPEN PAGE =================

    public void open() {
        driver.get("https://www.google.com");

        wait.until(ExpectedConditions.presenceOfElementLocated(searchBox));

        driver.findElement(searchBox).click();
    }

    // ================= SEARCH =================

    public void search(String text) {
        WebElement box = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchBox)
        );
        box.clear();
        box.sendKeys(text);
        box.submit();
    }

    // ================= SEARCH BOX =================

    public boolean isSearchBoxDisplayed() {
        return driver.findElement(searchBox).isDisplayed();
    }

    public boolean isSearchBoxEnabled() {
        return driver.findElement(searchBox).isEnabled();
    }

    // ================= TITLE =================

    public String getTitle() {
        return driver.getTitle();
    }

    // ================= GOOGLE LOGO =================

    public boolean isGoogleLogoDisplayed() {
        try {
            return driver.findElement(googleLogo).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ================= TOP LEFT =================

    public boolean isAboutDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(aboutLink)).isDisplayed();
    }

    public void clickAbout() {
        wait.until(ExpectedConditions.elementToBeClickable(aboutLink)).click();
    }

    public boolean isStoreDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(storeLink)).isDisplayed();
    }

    public void clickStore() {
        wait.until(ExpectedConditions.elementToBeClickable(storeLink)).click();
    }

    // ================= TOP RIGHT =================

    public boolean isGmailDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(gmailLink)).isDisplayed();
    }

    public void clickGmail() {
        wait.until(ExpectedConditions.elementToBeClickable(gmailLink)).click();
    }

    public boolean isImagesDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(imagesLink)).isDisplayed();
    }

    public void clickImages() {
        wait.until(ExpectedConditions.elementToBeClickable(imagesLink)).click();
    }

    // ================= APPS GRID =================

    public boolean isAppsGridDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(appsGrid)).isDisplayed();
    }

    public void clickAppsGrid() {
        wait.until(ExpectedConditions.elementToBeClickable(appsGrid)).click();
    }

    // ================= SEARCH BUTTON =================

    public boolean isSearchButtonDisplayed() {
        try {
            driver.findElement(googleSearchBtn);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ================= FEELING LUCKY =================

    public boolean isFeelingLuckyDisplayed() {
        try {
            driver.findElement(feelingLuckyBtn);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickFeelingLucky() {
        wait.until(ExpectedConditions.elementToBeClickable(feelingLuckyBtn)).click();
    }

    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(googleSearchBtn)).click();
    }

    // ================= FIX ADDED (IMPORTANT) =================
    // Used by tests to verify search worked reliably in CI/headless

    public boolean isOnResultsPage() {
        try {
            return driver.getCurrentUrl().contains("/search");
        } catch (Exception e) {
            return false;
        }
    }
}
