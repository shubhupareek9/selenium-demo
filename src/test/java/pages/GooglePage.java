package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GooglePage {

    WebDriver driver;
    WebDriverWait wait;

    // ===== Locators =====
    private By searchBox = By.name("q");

    // FIXED: robust Google logo locator (SVG + fallback)
    private By googleLogo = By.cssSelector("img[alt*='Google'], div[aria-label*='Google']");

    private By aboutLink = By.linkText("About");
    private By storeLink = By.linkText("Store");

    private By gmailLink = By.linkText("Gmail");
    private By imagesLink = By.linkText("Images");

    private By appsGrid = By.cssSelector("a[aria-label='Google apps']");

    // FIXED: more stable selectors
    private By googleSearchBtn = By.cssSelector("input[value='Google Search']");
    private By feelingLuckyBtn = By.cssSelector("input[value*='Lucky']");

    // ===== Constructor =====
    public GooglePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ===== Open page =====
    public void open() {
        driver.get("https://www.google.com");

        // ensure page is loaded
        wait.until(ExpectedConditions.presenceOfElementLocated(searchBox));
    }

    // ===== Search =====
    public void search(String text) {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        box.clear();
        box.sendKeys(text);
        box.submit();
    }

    // ===== Search box =====
    public boolean isSearchBoxDisplayed() {
        return driver.findElement(searchBox).isDisplayed();
    }

    public boolean isSearchBoxEnabled() {
        return driver.findElement(searchBox).isEnabled();
    }

    // ===== Title =====
    public String getTitle() {
        return driver.getTitle();
    }

    // ===== Google Logo (FIXED) =====
    public boolean isGoogleLogoDisplayed() {
        try {
            return driver.findElement(googleLogo).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ===== Top-left =====
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

    // ===== Top-right =====
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

    // ===== Apps grid =====
    public boolean isAppsGridDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(appsGrid)).isDisplayed();
    }

    public void clickAppsGrid() {
        wait.until(ExpectedConditions.elementToBeClickable(appsGrid)).click();
    }

    // ===== Buttons (FIXED) =====
    public boolean isSearchButtonDisplayed() {
        try {
            return driver.findElement(googleSearchBtn).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFeelingLuckyDisplayed() {
        try {
            return driver.findElement(feelingLuckyBtn).isDisplayed();
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
}
