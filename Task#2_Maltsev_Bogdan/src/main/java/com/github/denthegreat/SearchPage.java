package com.github.denthegreat;

import java.util.List;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Data
public class SearchPage {

  private static final String SEARCH_PAGE = "https://www.amazon.com/";

  private final WebDriver driver;

  @FindBy(id = "nav-search-bar-form")
  private WebElement searchForm;

  @FindBy(id = "s-refinements")
  private WebElement filterBar;

  @FindBy(id = "twotabsearchtextbox")
  private WebElement searchInput;

  public SearchPage(WebDriver driver) {
    this.driver = driver;
    driver.get(SEARCH_PAGE);
    PageFactory.initElements(driver, this);
  }

  public void submitSearch() {
    searchForm.submit();
  }

  public void populateSearchInput(String searchString) {
    searchInput.clear();
    searchInput.sendKeys(searchString);
  }

  public List<WebElement> findSearchedElements(String tagName, String text) {
    return driver.findElements(By.tagName(tagName)).stream()
        .filter(e -> e.getText().toLowerCase().contains(text))
        .toList();
  }

  public void close() {
    driver.close();
  }
}
