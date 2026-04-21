package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GooglePage;

public class GoogleTest extends BaseTest {

    @Test
    public void test01_openGoogle() {
        GooglePage googlePage = new GooglePage(driver);
        googlePage.open();

        Assert.assertTrue(googlePage.getTitle().contains("Google"),
                "Google page did not open");
    }

    @Test
    public void test02_searchBoxVisible() {
        GooglePage googlePage = new GooglePage(driver);
        googlePage.open();

        Assert.assertTrue(googlePage.isSearchBoxDisplayed(),
                "Search box is not visible");
    }

    @Test
    public void test03_pageTitle() {
        GooglePage googlePage = new GooglePage(driver);
        googlePage.open();

        String title = googlePage.getTitle();
        Assert.assertTrue(title.contains("Google"),
                "Page title is incorrect");
    }

    @Test
    public void test04_searchMilk() {
        GooglePage googlePage = new GooglePage(driver);
        googlePage.open();

        googlePage.search("milk");

        Assert.assertTrue(googlePage.isOnResultsPage(),
                "User is not navigated to search results page");
    }
}
