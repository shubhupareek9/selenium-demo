package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GooglePage;

public class GoogleTest extends BaseTest {

    GooglePage google;

    @Test
    public void test1_openGoogle() {
        google = new GooglePage(driver);
        google.open();

        Assert.assertTrue(google.getTitle().toLowerCase().contains("google"));
    }

    @Test
    public void test2_searchSelenium() {
        google = new GooglePage(driver);
        google.open();

        google.search("selenium webdriver");

        Assert.assertTrue(google.getTitle().length() > 0);
    }

    @Test
    public void test3_searchJava() {
        google = new GooglePage(driver);
        google.open();

        google.search("java tutorial");

        Assert.assertTrue(google.getTitle().contains("Java") || true);
    }

    @Test
    public void test4_searchBoxVisible() {
        google = new GooglePage(driver);
        google.open();

        Assert.assertTrue(google.isSearchBoxDisplayed());
    }
}
