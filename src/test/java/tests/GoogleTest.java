package tests;

import base.BaseTest;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.GooglePage;
import pages.GoogleResultsPage;
import utils.JsonReader;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class GoogleTest extends BaseTest {

    // =========================
    // BASIC PAGE LOAD TESTS
    // =========================

    @Test(description = "Verify Google homepage opens successfully and title contains 'Google'")
    public void test01_openGooglePage() {

        Reporter.log("Step 1: Open Google homepage", true);

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
    // SEARCH TESTS
    // =========================

    @Test(description = "Search for a single keyword and verify results page is displayed")
    public void test03_singleSearch() {

        GooglePage google = new GooglePage(driver);
        google.open();

        google.search("milk");

        assertTrue(google.isOnResultsPage(),
                "Search results not loaded for milk");
    }

    @DataProvider(name = "searchData")
    public Object[] getSearchData() {
        List<String> terms = JsonReader.getSearchTerms(
                "src/test/resources/testdata/searchData.json"
        );
        return terms.toArray();
    }

    @Test(dataProvider = "searchData")
    public void test04_multipleSearch(String term) {

        GooglePage google = new GooglePage(driver);
        google.open();

        google.search(term);

        assertTrue(google.isOnResultsPage(),
                "Search failed for: " + term);
    }

    // =========================
    // UI TESTS (UNCHANGED)
    // =========================

    @Test
    public void test05_topLeftAboutStore() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isAboutDisplayed());
        assertTrue(google.isStoreDisplayed());
    }

    @Test
    public void test06_topRightLinks() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isGmailDisplayed());
        assertTrue(google.isImagesDisplayed());
    }

    @Test
    public void test07_appsGrid() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isAppsGridDisplayed());
        google.clickAppsGrid();
    }

    @Test
    public void test08_googleLogo() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isGoogleLogoDisplayed());
    }

    @Test
    public void test09_searchAndFeelingLuckyButtons() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isSearchButtonDisplayed());
        assertTrue(google.isFeelingLuckyDisplayed());

        google.clickFeelingLucky();
    }

    @Test
    public void test10_searchBoxEnabledAndEnterKey() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isSearchBoxEnabled());

        google.search("selenium");

        assertTrue(google.isOnResultsPage());
    }

    @Test
    public void test11_specialCharacterSearch() {
        GooglePage google = new GooglePage(driver);
        google.open();

        google.search(")(*()(&*(&(*&*^&%^&*^((&&&)(*)(*)");

        assertTrue(google.isOnResultsPage());
    }

    // =========================
    // SPLIT TEST 12 (NEW CLEAN STRUCTURE)
    // =========================

    private GoogleResultsPage openResults() {
        GooglePage google = new GooglePage(driver);
        GoogleResultsPage results = new GoogleResultsPage(driver);

        results.openSearchResults("apples", google);
        return results;
    }

    @Test
    public void test12_verifyAllTab() {
        GoogleResultsPage results = openResults();
        assertTrue(results.isTabPresent("All"));
        results.clickTab("All");
    }

    @Test
    public void test13_verifyShoppingTab() {
        GoogleResultsPage results = openResults();
        assertTrue(results.isTabPresent("Shopping"));
        results.clickTab("Shopping");
    }

    @Test
    public void test14_verifyVideosTab() {
        GoogleResultsPage results = openResults();
        assertTrue(results.isTabPresent("Videos"));
        results.clickTab("Videos");
    }

    @Test
    public void test15_verifyImagesTab() {
        GoogleResultsPage results = openResults();
        assertTrue(results.isTabPresent("Images"));
        results.clickTab("Images");
    }

    @Test
    public void test16_verifyNewsTab() {
        GoogleResultsPage results = openResults();
        assertTrue(results.isTabPresent("News"));
        results.clickTab("News");
    }

    @Test
    public void test17_verifyToolsButton() {
        GoogleResultsPage results = openResults();

        assertTrue(results.isToolsDisplayed());
        results.clickTools();
    }
}
