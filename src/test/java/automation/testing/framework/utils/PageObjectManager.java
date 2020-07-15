package automation.testing.framework.utils;

import automation.testing.framework.config.EnvironmentHandler;
import automation.testing.framework.selenium.WebDriverHandler;
import lombok.Getter;

@Getter
public class PageObjectManager {

  private WebDriverHandler webDriverHandler;

  private EnvironmentHandler environmentHandler;


  public PageObjectManager(
      WebDriverHandler webDriverHandler,
      EnvironmentHandler environmentHandle
      ) {
    this.webDriverHandler = webDriverHandler;
    this.environmentHandler = environmentHandle;
  }
}
