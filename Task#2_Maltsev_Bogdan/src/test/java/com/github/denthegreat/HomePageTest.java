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
public class HomePageTest {

  final static ChromeDriver DRIVER = new ChromeDriver();
  final static HomePage HOME_PAGE = new HomePage(DRIVER);

  @BeforeAll
  public static void setupChromeDriver() {
    WebDriverManager.chromedriver().setup();
  }

  @Test
  void verifyAmazonTitle() {
    // when
    String title = HOME_PAGE.getTitle();

    // then
    assertThat(title)
        .isNotNull()
        .isEqualTo("Amazon.com. Spend less. Smile more.");
  }

  @Test
  void verifyPageIsNotEmpty() {
    // when
    List<WebElement> elements = HOME_PAGE.getElements();

    // then
    assertThat(elements)
        .isNotEmpty()
        .hasSizeGreaterThan(1);
  }

  @Test
  void verifySearchDropdownIsPresent() {
    // when
    WebElement searchDropdown = HOME_PAGE.getSearchDropdown();

    // then
    assertThat(searchDropdown).isNotNull();
  }

  @Test
  void verifySearchDropdownDefaultValue() {
    // given
    WebElement searchDropdown = HOME_PAGE.getSearchDropdown();

    // when
    WebElement defaultOption = searchDropdown.findElements(By.tagName("option")).stream()
        .filter(e -> e.isSelected()).findFirst().orElse(null);

    // then
    assertThat(defaultOption).isNotNull();
    assertThat(defaultOption.getText()).isEqualTo("All Departments");
  }

  @Test
  void verifyBannerIsPresent() {
    // when
    WebElement banner = HOME_PAGE.getBanner();

    // then
    assertThat(banner).isNotNull();
  }
}
