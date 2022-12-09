package com.github.denthegreat;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.denthegreat.config.ReplaceCamelCase;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@DisplayNameGeneration(ReplaceCamelCase.class)
public class TodayDealsPageTest {

  final static ChromeDriver DRIVER = new ChromeDriver();
  final static TodayDealsPage TODAY_DEALS_PAGE = new TodayDealsPage(DRIVER);

  @BeforeAll
  public static void setupChromeDriver() {
    WebDriverManager.chromedriver().setup();
  }

  @Test
  void verifyFeaturedProductsWidgetIsPresent() {
    // when
    WebElement featuredProductsWidget = TODAY_DEALS_PAGE.getFeaturedProductsWidget();

    // then
    assertThat(featuredProductsWidget).isNotNull();
  }

  @Test
  void verifyFeaturedProductsListIsNotEmpty() {
    // given
    TODAY_DEALS_PAGE.navigateToTodayDealsPage();

    // when
    List<WebElement> featuredProductsList = TODAY_DEALS_PAGE.findFeaturedProductsInWidget();

    // then
    assertThat(featuredProductsList).isNotEmpty().hasSizeGreaterThan(1);
  }

  @Test
  void verifyDealsFilterIsPresent() {
    // given
    WebElement dealsFilterContainer = TODAY_DEALS_PAGE.getDealsFilterContainer();

    // when
    List<String> listOfFilters = dealsFilterContainer.findElement(By.tagName("ul"))
        .findElements(By.tagName("li"))
        .stream()
        .map(e -> e.findElement(By.tagName("span")))
        .map(WebElement::getText)
        .toList();

    // then
    assertThat(listOfFilters)
        .isNotEmpty()
        .hasSizeGreaterThan(1)
        .contains("All deals", "10% off or more", "50% off or more");
  }

  @Test
  void verifySelectedDeal() {
    // given
    List<WebElement> deals = TODAY_DEALS_PAGE.findDeals();

    // when
    WebElement deal = deals.get(1);
    deal.click();

    // then
    assertThat(TODAY_DEALS_PAGE.getSelectedDeals())
        .isNotEmpty()
        .hasSizeGreaterThan(1);
  }

}
