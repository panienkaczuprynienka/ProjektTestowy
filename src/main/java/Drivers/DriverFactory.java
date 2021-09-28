package Drivers;

import Utils.ConfigurationManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
  private RemoteWebDriver driver;

  public WebDriver create() throws MalformedURLException {
    Browser browserType = Browser.valueOf(ConfigurationManager.getInstance().getBrowser());
    switch (browserType) {
      case CHROME:
        return createChromeDriver();
      case FIREFOX:
        return createFirefoxDriver();
      default:
        throw new IllegalArgumentException("Provided browser doesnt exist");
    }

  }

  private WebDriver createFirefoxDriver()  {
    FirefoxOptions options = new FirefoxOptions();
    options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
    return getDriver(options);
  }

  private WebDriver createChromeDriver()  {
    ChromeOptions options = new ChromeOptions();
      return getDriver(options);
  }

  public WebDriver getDriver(MutableCapabilities options){
    try {
      driver = new RemoteWebDriver(new URL(ConfigurationManager.getInstance().getHubUrl()), options);
    } catch (MalformedURLException e) {
      e.printStackTrace();
      System.out.println("check configuration %s" + ConfigurationManager.getInstance().getConfigurationLocation());
    }
    return driver;
  }
}
