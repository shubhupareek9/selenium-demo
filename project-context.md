# PROJECT_CONTEXT.md

## Selenium TestNG Google Automation Framework

---

# 1. Project Overview

This is a **Selenium WebDriver + TestNG + Maven automation framework** designed to test Google search functionality using:

* Page Object Model (POM)
* Data-driven testing (JSON)
* GitHub Actions CI execution (headless Linux Chrome)
* TestNG reporting (Reporter logs + Surefire reports)

---

# 2. Project Structure

```
selenium-testng-google/
│
├── .github/workflows/
│   └── tests.yml
│
├── src/test/java/
│   ├── base/
│   │   └── BaseTest.java
│   │
│   ├── pages/
│   │   ├── GooglePage.java
│   │   └── GoogleResultsPage.java
│   │
│   ├── tests/
│   │   └── GoogleTest.java
│   │
│   └── utils/
│       └── JsonReader.java
│
├── src/test/resources/
│   └── testdata/
│       └── searchData.json
│
├── pom.xml
└── testng.xml
```

---

# 3. Execution Flow

## 3.1 Test Execution Lifecycle

1. `BaseTest.setup()`

   * Initializes ChromeDriver (headless mode)
   * Applies:

     * `--headless=new`
     * `--no-sandbox`
     * `--disable-dev-shm-usage`

2. `GooglePage.open()`

   * Opens https://www.google.com
   * Waits for search box

3. User actions:

   * `search(text)`
   * click or submit search

4. `GoogleResultsPage`

   * waits for results (`div#rso` OR `h3`)
   * extracts top results

5. Assertions in TestNG validate:

   * page navigation
   * search success
   * results presence

6. `BaseTest.tearDown()`

   * quits browser

---

# 4. Page Object Model Design

## 4.1 GooglePage (Home Page)

### Responsibilities:

* Open Google homepage
* Perform search
* Validate UI elements

### Key Methods:

* `open()`
* `search(String text)`
* `isSearchBoxDisplayed()`
* `isOnResultsPage()`

### Key Locators:

* `name=q`
* `name=btnK`
* `name=btnI`
* links: Gmail, Images, About, Store

---

## 4.2 GoogleResultsPage (Results Page)

### Responsibilities:

* Wait for results page load
* Extract search results

### Key Methods:

* `waitForResultsPage()`
* `getTopSearchResults(int limit)`

### Result extraction strategy:

* Primary: `div#rso h3`
* Fallback: all `h3` tags

---

# 5. Test Layer (GoogleTest)

## 5.1 Test Categories

### Basic UI Tests

* homepage open
* search box visibility
* logo presence
* links validation

### Functional Tests

* single keyword search
* Enter-key search
* special character search

### Data-driven Tests

* Uses `searchData.json`
* Provided via `JsonReader`

---

## 5.2 New Stable Test (Important Replacement)

### `test12_printTop10SearchResults`

Flow:

1. Open Google
2. Search: `"apples"`
3. Wait for results page
4. Extract top 10 results
5. Print them using `Reporter.log`

Purpose:

* replaces unstable tab-based tests
* validates real search output instead of UI tabs

---

# 6. Data Layer

## JsonReader.java

Reads:

```json
{
  "searchTerms": ["apple", "selenium", "milk"]
}
```

Returns:

* `List<String>` of search terms

Used in:

* `@DataProvider searchData`

---

# 7. CI/CD Pipeline (GitHub Actions)

## Workflow: `.github/workflows/tests.yml`

### Steps:

1. Checkout repo
2. Setup JDK 17
3. Cache Maven dependencies
4. Run:

   ```
   mvn clean test
   ```
5. Upload:

   * Extent report
   * Surefire reports

### Environment:

* Ubuntu latest
* Headless Chrome execution

---

# 8. Known Issues / Fragility Points

## 8.1 Google DOM instability

* Selectors like `div#search` or `div#rso` may change
* Google UI varies by region / bot detection

## 8.2 Timing issues in CI

* Headless mode slower rendering
* 10s wait may be insufficient in CI

## 8.3 CDP warning (non-blocking)

```
Unable to find CDP implementation matching 147
```

* harmless warning from Selenium vs Chrome mismatch

## 8.4 Flaky result detection

* `h3` elements may include:

  * ads
  * navigation items
  * unrelated headers

---

# 9. Debugging Strategy (IMPORTANT)

When tests fail:

### Step 1:

Check `target/surefire-reports`

### Step 2:

Verify:

* page load completed
* correct URL (`/search`)
* elements exist (`h3`, `rso`)

### Step 3:

If CI fails but local passes:

* increase wait time
* add explicit waits
* inspect headless DOM differences

---

# 10. Recommended Improvements (Future)

* Replace `h3` scraping with:

  * `div#search` → more stable container (if available)
* Add explicit wait for URL:

  ```
  wait.until(urlContains("/search"))
  ```
* Add retry mechanism for flaky CI tests
* Add PageFactory or stronger locator strategy
* Add logging instead of only Reporter.log

---

# 11. Summary

This framework is:

* POM-based Selenium suite
* TestNG-driven execution
* CI-ready (GitHub Actions)
* partially data-driven via JSON
* optimized for Google search validation

Main weakness:

* reliance on dynamic Google DOM selectors

Main strength:

* clean structure + extensible test design
