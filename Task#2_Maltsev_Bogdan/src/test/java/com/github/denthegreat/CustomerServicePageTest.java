package com.github.denthegreat;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.denthegreat.config.ReplaceCamelCase;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@DisplayNameGeneration(ReplaceCamelCase.class)
public class CustomerServicePageTest {

  final static ChromeDriver DRIVER = new ChromeDriver();
  final static CustomerServicePage CUSTOMER_SERVICE_PAGE = new CustomerServicePage(DRIVER);

  @BeforeAll
  public static void setupChromeDriver() {
    WebDriverManager.chromedriver().setup();
  }

  @Test
  void verifyCustomerServicePageTitle() {
    // when
    String pageTitle = CUSTOMER_SERVICE_PAGE.getPageTitle();

    // then
    assertThat(pageTitle).isEqualTo("Welcome to Amazon Customer Service");
  }

  @Test
  void verifyHelpTopicsListIsNotEmpty() {
    // when
    List<WebElement> helpTopics = CUSTOMER_SERVICE_PAGE.getHelpTopics();

    // then
    assertThat(helpTopics)
        .isNotEmpty()
        .hasSizeGreaterThan(1);
  }

  @Test
  void verifyHelpCardsAreNotTheSame() throws InterruptedException {
    // given
    List<WebElement> helpTopics = CUSTOMER_SERVICE_PAGE.getHelpTopics();

    // when
    helpTopics.get(0).click();
    Thread.sleep(2500);
    List<String> currentHelpCards = CUSTOMER_SERVICE_PAGE.getCurrentHelpCards();


    helpTopics.get(1).click();
    Thread.sleep(2500);
    List<String> selectedHelpCards = CUSTOMER_SERVICE_PAGE.getCurrentHelpCards();

    // then
    assertThat(currentHelpCards).isNotEqualTo(selectedHelpCards);
  }
}
