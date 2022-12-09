package com.github.denthegreat;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.denthegreat.config.ReplaceCamelCase;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@DisplayNameGeneration(ReplaceCamelCase.class)
public class SearchPageTest {

  final static ChromeDriver DRIVER = new ChromeDriver();
  final static SearchPage SEARCH_PAGE = new SearchPage(DRIVER);

  @BeforeAll
  public static void setupChromeDriver() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeEach
  public void performDummySearch() {
    String searchString = "";
    SEARCH_PAGE.populateSearchInput(searchString);
    SEARCH_PAGE.submitSearch();
  }

  @Test
  void verifySearchResult() {
    // given
    String searchString = "iphone";
    SEARCH_PAGE.populateSearchInput(searchString);

    // when
    SEARCH_PAGE.submitSearch();
    List<WebElement> searchedElements = SEARCH_PAGE.findSearchedElements("span", searchString);

    // then
    assertThat(searchedElements).isNotEmpty();
  }

  @Test
  void verifyFilterBarIsPresent() {
    // when
    WebElement filterBar = SEARCH_PAGE.getFilterBar();

    // then
    assertThat(filterBar).isNotNull();
  }

  @Test
  void verifyBrandsFilterForPhoneSearchString() {
    // given
    String searchString = "phone";
    SEARCH_PAGE.populateSearchInput(searchString);

    // when
    SEARCH_PAGE.submitSearch();
    WebElement filterBar = SEARCH_PAGE.getFilterBar();
    List<String> brands = filterBar.findElement(By.id("brandsRefinements"))
        .findElement(By.tagName("ul"))
        .findElements(By.tagName("li"))
        .stream()
        .map(e -> e.findElement(By.tagName("span")))
        .map(WebElement::getText)
        .map(String::toLowerCase)
        .toList();

    // then
    assertThat(brands)
        .isNotEmpty()
        .contains("oneplus", "samsung");
  }

}
