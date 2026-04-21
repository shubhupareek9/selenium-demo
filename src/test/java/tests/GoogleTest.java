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
    public void test04_searchMultipleItems() {

         GooglePage googlePage = new GooglePage(driver);
         googlePage.open();

         String[] items = {"milk", "butter", "cheese", "nuts"};

        for (String item : items) {

               // perform search
               googlePage.search(item);

              // verify navigation to results page
               Assert.assertTrue(
                googlePage.isOnResultsPage(),
                "User is not navigated to results page for: " + item
        );

        // go back to Google homepage for next search
        driver.navigate().back();
    }
}
}
