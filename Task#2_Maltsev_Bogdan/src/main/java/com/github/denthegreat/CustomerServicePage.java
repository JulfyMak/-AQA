package com.github.denthegreat;

import java.util.List;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Data
public class CustomerServicePage {

  private static final String CUSTOMER_SERVICE_PAGE_URL = "https://www.amazon.com/gp/help/customer/display.html?nodeId=508510&ref_=nav_cs_customerservice";

  private WebDriver driver;

  @FindBy(className = "page-container")
  private WebElement pageContainer;

  @FindBy(xpath = "//li[@class='help-topics']")
  private List<WebElement> helpTopics;

  public CustomerServicePage(WebDriver driver) {
    this.driver = driver;
    driver.get(CUSTOMER_SERVICE_PAGE_URL);
    PageFactory.initElements(driver, this);
  }

  public String getPageTitle() {
    return pageContainer.findElement(By.tagName("h1")).getText();
  }

  public List<String> getCurrentHelpCards() {
    return driver.findElements(By.xpath("//div[@class='help-card-wrapper']"))
        .stream()
        .map(e -> e.findElement(By.xpath("//div[@class='fs-match-card-title']")))
        .map(WebElement::getText)
        .toList();
  }

  public void close() {
    driver.close();
  }
}
