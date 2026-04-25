package tests;

import base.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.GooglePage;
import utils.JsonReader;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class GoogleTest extends BaseTest {

    // =========================
    // BASIC PAGE LOAD TESTS
    // =========================

    @Test(description = "Verify Google homepage opens successfully and title contains 'Google'")
    public void test01_openGooglePage() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.getTitle().toLowerCase().contains("google"),
                "Google page did not open properly");
    }

    @Test(description = "Verify search box is visible on Google homepage")
    public void test02_searchBoxDisplayed() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isSearchBoxDisplayed(),
                "Search box is not displayed");
    }

    // =========================
    // SEARCH TESTS (FIXED)
    // =========================

    @Test(description = "Search for a single keyword and verify results page is displayed")
    public void test03_singleSearch() {
        GooglePage google = new GooglePage(driver);
        google.open();

        google.search("milk");

        assertTrue(google.isOnResultsPage(),
                "Search results not loaded for milk");
    }

    // =========================
    // DATA DRIVEN TEST
    // =========================

    @DataProvider(name = "searchData")
    public Object[] getSearchData() {
        List<String> terms = JsonReader.getSearchTerms(
                "src/test/resources/testdata/searchData.json"
        );
        return terms.toArray();
    }

    @Test(
        description = "Perform multiple searches using JSON data and verify results page loads",
        dataProvider = "searchData"
    )
    public void test04_multipleSearch(String term) {

        GooglePage google = new GooglePage(driver);

        google.open();
        google.search(term);

        assertTrue(google.isOnResultsPage(),
                "Search failed for: " + term);
    }

    // =========================
    // UI TESTS
    // =========================

    @Test(description = "Verify About and Store links are visible on top-left of homepage")
    public void test05_topLeftAboutStore() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isAboutDisplayed(), "About not visible");
        assertTrue(google.isStoreDisplayed(), "Store not visible");
    }

    @Test(description = "Verify Gmail and Images links are visible on top-right of homepage")
    public void test06_topRightLinks() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isGmailDisplayed(), "Gmail not visible");
        assertTrue(google.isImagesDisplayed(), "Images not visible");
    }

    @Test(description = "Verify Google Apps grid icon is visible and clickable")
    public void test07_appsGrid() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isAppsGridDisplayed(), "Apps grid not visible");

        google.clickAppsGrid();
    }

    @Test(description = "Verify Google logo is displayed on homepage")
    public void test08_googleLogo() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isGoogleLogoDisplayed(),
                "Google logo not visible");
    }

    @Test(description = "Verify Search and I'm Feeling Lucky buttons are visible and clickable")
    public void test09_searchAndFeelingLuckyButtons() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isSearchButtonDisplayed(), "Search button missing");
        assertTrue(google.isFeelingLuckyDisplayed(), "I'm Feeling Lucky missing");

        google.clickFeelingLucky();
    }

    @Test(description = "Verify search box is enabled and Enter key triggers search successfully")
    public void test10_searchBoxEnabledAndEnterKey() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isSearchBoxEnabled(), "Search box not enabled");

        google.search("selenium");

        assertTrue(google.isOnResultsPage(),
                "Enter key search failed");
    }
}
