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
    private By googleLogo = By.cssSelector("img[alt='Google']");

    private By aboutLink = By.linkText("About");
    private By storeLink = By.linkText("Store");

    private By gmailLink = By.linkText("Gmail");
    private By imagesLink = By.linkText("Images");

    private By appsGrid = By.cssSelector("a[aria-label='Google apps']");

    private By googleSearchBtn = By.name("btnK");
    private By feelingLuckyBtn = By.name("btnI");

    // ===== Constructor =====
    public GooglePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ===== Open page =====
    public void open() {
        driver.get("https://www.google.com");

        // wait for search box to ensure page is loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
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

    // ===== Google Logo =====
    public boolean isGoogleLogoDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(googleLogo)).isDisplayed();
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

    // ===== Buttons =====
    public boolean isSearchButtonDisplayed() {
        return driver.findElement(googleSearchBtn).isDisplayed();
    }

    public boolean isFeelingLuckyDisplayed() {
        return driver.findElement(feelingLuckyBtn).isDisplayed();
    }

    public void clickFeelingLucky() {
        wait.until(ExpectedConditions.elementToBeClickable(feelingLuckyBtn)).click();
    }

    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(googleSearchBtn)).click();
    }
}
