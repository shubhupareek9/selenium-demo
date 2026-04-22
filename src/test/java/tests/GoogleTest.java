package tests;

import base.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.GooglePage;
import utils.JsonReader;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GoogleTest extends BaseTest {

    // ✅ Test 1: Verify Google page opens
    @Test
    public void test01_openGooglePage() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.getTitle().toLowerCase().contains("google"),
                "Google page did not open properly");
    }

    // ✅ Test 2: Verify search box is visible
    @Test
    public void test02_searchBoxDisplayed() {
        GooglePage google = new GooglePage(driver);
        google.open();

        assertTrue(google.isSearchBoxDisplayed(),
                "Search box is not displayed");
    }

    // ✅ Test 3: Search a single term and verify result page
    @Test
    public void test03_singleSearch() {
        GooglePage google = new GooglePage(driver);
        google.open();

        google.search("milk");

        assertTrue(google.getTitle().toLowerCase().contains("milk"),
                "Search results not loaded for milk");
    }

    // ✅ DataProvider (reads from JSON)
    @DataProvider(name = "searchData")
    public Object[] getSearchData() {
        List<String> terms = JsonReader.getSearchTerms(
                "src/test/resources/testdata/searchData.json"
        );
        return terms.toArray();
    }

    // ✅ Test 4: Multiple searches from JSON (milk, butter, cheese, nuts...)
    @Test(dataProvider = "searchData")
    public void test04_multipleSearch(String term) {

        GooglePage google = new GooglePage(driver);

        google.open();
        google.search(term);

        String title = google.getTitle().toLowerCase();

        assertTrue(title.contains(term.toLowerCase()),
                "Search failed for: " + term);
    }
}
