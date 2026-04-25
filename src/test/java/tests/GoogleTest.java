package tests;

import base.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.GooglePage;
import utils.JsonReader;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class GoogleTest extends BaseTest {

    // ✅ Test 1
    @Test
    public void test01_openGooglePage() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.getTitle().toLowerCase().contains("google"),
                "Google page did not open properly");
    }

    // ✅ Test 2
    @Test
    public void test02_searchBoxDisplayed() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isSearchBoxDisplayed(),
                "Search box is not displayed");
    }

    // ✅ Test 3
    @Test
    public void test03_singleSearch() {
        GooglePage google = new GooglePage(driver);
        google.open();

        google.search("milk");

        assertTrue(google.getTitle().toLowerCase().contains("milk"),
                "Search results not loaded for milk");
    }

    // ✅ DataProvider
    @DataProvider(name = "searchData")
    public Object[] getSearchData() {
        List<String> terms = JsonReader.getSearchTerms(
                "src/test/resources/testdata/searchData.json"
        );
        return terms.toArray();
    }

    // ✅ Test 4
    @Test(dataProvider = "searchData")
    public void test04_multipleSearch(String term) {

        GooglePage google = new GooglePage(driver);

        google.open();
        google.search(term);

        String title = google.getTitle().toLowerCase();

        assertTrue(title.contains(term.toLowerCase()),
                "Search failed for: " + term);
    }

    // =========================
    // NEW UI TESTS (ADDED)
    // =========================

    @Test
    public void test05_topLeftAboutStore() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isAboutDisplayed(), "About not visible");
        assertTrue(google.isStoreDisplayed(), "Store not visible");
    }

    @Test
    public void test06_topRightLinks() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isGmailDisplayed(), "Gmail not visible");
        assertTrue(google.isImagesDisplayed(), "Images not visible");
    }

    @Test
    public void test07_appsGrid() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isAppsGridDisplayed(), "Apps grid not visible");

        google.clickAppsGrid(); // verify clickable
    }

    @Test
    public void test08_googleLogo() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isGoogleLogoDisplayed(),
                "Google logo not visible");
    }

    @Test
    public void test09_searchAndFeelingLuckyButtons() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isSearchButtonDisplayed(), "Search button missing");
        assertTrue(google.isFeelingLuckyDisplayed(), "I'm Feeling Lucky missing");

        google.clickFeelingLucky();
    }

    @Test
    public void test10_searchBoxEnabledAndEnterKey() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isSearchBoxEnabled(), "Search box not enabled");

        google.search("selenium"); // already tests Enter/submit behavior
        assertTrue(google.getTitle().toLowerCase().contains("selenium"),
                "Enter key search failed");
    }
}
