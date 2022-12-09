package com.github.denthegreat;

import java.util.List;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Data
public class HomePage {

  public static final String HOME_PAGE_URL = "https://www.amazon.com/";

  private WebDriver driver;

  @FindBy(tagName = "body")
  private WebElement body;

  @FindBy(id = "searchDropdownBox")
  private WebElement searchDropdown;

  @FindBy(id = "desktop-banner")
  private WebElement banner;

  public HomePage(WebDriver driver) {
    this.driver = driver;
    driver.get(HOME_PAGE_URL);
    PageFactory.initElements(driver, this);
  }

  public String getTitle() {
    return driver.getTitle();
  }

  public List<WebElement> getElements() {
    return body.findElements(By.tagName("div"));
  }

  public WebElement getSearchDropdown() {
    return searchDropdown;
  }

  public WebElement getBanner() {
    return banner;
  }

  public void close() {
    driver.close();
  }
}
