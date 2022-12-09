package com.github.denthegreat;

import java.util.List;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Data
public class TodayDealsPage {

  private static final String TODAY_DEALS_PAGE_URL = "https://www.amazon.com/gp/goldbox?ref_=nav_cs_gb";

  private final WebDriver driver;

  @FindBy(xpath = "//span[@data-testid='grid-filter-DISCOUNT']")
  private WebElement dealsFilterContainer;

  @FindBy(xpath = "//ol[@role='list']")
  private WebElement featuredProductsWidget;

  public TodayDealsPage(WebDriver driver) {
    this.driver = driver;
    driver.get(TODAY_DEALS_PAGE_URL);
    PageFactory.initElements(driver, this);
  }

  public List<WebElement> findFeaturedProductsInWidget() {
    return featuredProductsWidget.findElements(By.tagName("li"));
  }

  public List<WebElement> getSelectedDeals() {
    return driver.findElement(By.xpath("//div[@data-mix-claimed='true']"))
        .findElements(By.tagName("div"));
  }

  public List<WebElement> findDeals() {
    return driver.findElements(By.xpath("//div[@data-testid='deal-card']"));
  }

  public void navigateToTodayDealsPage() {
    driver.get(TODAY_DEALS_PAGE_URL);
  }

  public void close() {
    driver.close();
  }
}
