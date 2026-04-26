package tests;

import base.BaseTest;
import org.testng.Reporter;
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

        Reporter.log("Step 1: Open Google homepage", true);

        GooglePage google = new GooglePage(driver);
        google.open();

        Reporter.log("Step 2: Validate page title contains 'Google'", true);

        assertTrue(google.getTitle().toLowerCase().contains("google"),
                "Google page did not open properly");
    }

    @Test(description = "Verify search box is visible on Google homepage")
    public void test02_searchBoxDisplayed() {

        Reporter.log("Step 1: Open Google homepage", true);

        GooglePage google = new GooglePage(driver);
        google.open();

        Reporter.log("Step 2: Check search box visibility", true);

        assertTrue(google.isSearchBoxDisplayed(),
                "Search box is not displayed");
    }

    // =========================
    // SEARCH TESTS
    // =========================

    @Test(description = "Search for a single keyword and verify results page is displayed")
    public void test03_singleSearch() {

        Reporter.log("Step 1: Open Google homepage", true);

        GooglePage google = new GooglePage(driver);
        google.open();

        Reporter.log("Step 2: Enter search term 'milk' and submit", true);

        google.search("milk");

        Reporter.log("Step 3: Verify results page is loaded", true);

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

        Reporter.log("Step 1: Open Google homepage", true);

        GooglePage google = new GooglePage(driver);
        google.open();

        Reporter.log("Step 2: Search for term -> " + term, true);

        google.search(term);

        Reporter.log("Step 3: Verify results page is loaded for -> " + term, true);

        assertTrue(google.isOnResultsPage(),
                "Search failed for: " + term);
    }

    // =========================
    // UI TESTS
    // =========================

    @Test(description = "Verify About and Store links are visible on top-left of homepage")
    public void test05_topLeftAboutStore() {

        Reporter.log("Step 1: Open Google homepage", true);

        GooglePage google = new GooglePage(driver);
        google.open();

        Reporter.log("Step 2: Verify About link is visible", true);
        assertTrue(google.isAboutDisplayed(), "About not visible");

        Reporter.log("Step 3: Verify Store link is visible", true);
        assertTrue(google.isStoreDisplayed(), "Store not visible");
    }

    @Test(description = "Verify Gmail and Images links are visible on top-right of homepage")
    public void test06_topRightLinks() {

        Reporter.log("Step 1: Open Google homepage", true);

        GooglePage google = new GooglePage(driver);
        google.open();

        Reporter.log("Step 2: Verify Gmail link is visible", true);
        assertTrue(google.isGmailDisplayed(), "Gmail not visible");

        Reporter.log("Step 3: Verify Images link is visible", true);
        assertTrue(google.isImagesDisplayed(), "Images not visible");
    }

    @Test(description = "Verify Google Apps grid icon is visible and clickable")
    public void test07_appsGrid() {

        Reporter.log("Step 1: Open Google homepage", true);

        GooglePage google = new GooglePage(driver);
        google.open();

        Reporter.log("Step 2: Verify Apps grid is visible", true);
        assertTrue(google.isAppsGridDisplayed(), "Apps grid not visible");

        Reporter.log("Step 3: Click Apps grid", true);
        google.clickAppsGrid();
    }

    @Test(description = "Verify Google logo is displayed on homepage")
    public void test08_googleLogo() {

        Reporter.log("Step 1: Open Google homepage", true);

        GooglePage google = new GooglePage(driver);
        google.open();

        Reporter.log("Step 2: Verify Google logo is visible", true);

        assertTrue(google.isGoogleLogoDisplayed(),
                "Google logo not visible");
    }

    @Test(description = "Verify Search and I'm Feeling Lucky buttons are visible and clickable")
    public void test09_searchAndFeelingLuckyButtons() {

        Reporter.log("Step 1: Open Google homepage", true);

        GooglePage google = new GooglePage(driver);
        google.open();

        Reporter.log("Step 2: Verify Search button is present", true);
        assertTrue(google.isSearchButtonDisplayed(), "Search button missing");

        Reporter.log("Step 3: Verify I'm Feeling Lucky button is present", true);
        assertTrue(google.isFeelingLuckyDisplayed(), "I'm Feeling Lucky missing");

        Reporter.log("Step 4: Click I'm Feeling Lucky button", true);
        google.clickFeelingLucky();
    }

    @Test(description = "Verify search box is enabled and Enter key triggers search successfully")
    public void test10_searchBoxEnabledAndEnterKey() {

        Reporter.log("Step 1: Open Google homepage", true);

        GooglePage google = new GooglePage(driver);
        google.open();

        Reporter.log("Step 2: Verify search box is enabled", true);
        assertTrue(google.isSearchBoxEnabled(), "Search box not enabled");

        Reporter.log("Step 3: Perform search using Enter key (selenium)", true);
        google.search("selenium");

        Reporter.log("Step 4: Verify results page is loaded", true);
        assertTrue(google.isOnResultsPage(),
                "Enter key search failed");
    }
    @Test(description = "Verify Google handles special character search input without errors")
public void test11_specialCharacterSearch() {

    Reporter.log("Step 1: Open Google homepage", true);

    GooglePage google = new GooglePage(driver);
    google.open();

    String specialInput = ")(*()(&*(&(*&*^&%^&*^((&&&)(*)(*)";

    Reporter.log("Step 2: Enter special character search: " + specialInput, true);

    google.search(specialInput);

    Reporter.log("Step 3: Verify application does not crash and results page loads", true);

    assertTrue(google.isOnResultsPage(),
            "Search with special characters did not navigate to results page");
}
}
