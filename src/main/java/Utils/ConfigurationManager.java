package Utils;

import org.openqa.selenium.remote.BrowserType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {
  private static volatile ConfigurationManager instance;
  private String hubUrl;
  private String baseUrl;
  private String browser;
  public final String configurationLocation = "src/configs/Configurations.properties";

  private ConfigurationManager() {
    loadData();
  }

  public static ConfigurationManager getInstance() {
    if (instance == null) {
      synchronized (ConfigurationManager.class) {
        if (instance == null) {
          instance = new ConfigurationManager();
        }
      }
    }
    return instance;
  }

  public void loadData() {
    Properties properties = new Properties();
    try {
      properties.load(new FileInputStream(configurationLocation));
    } catch (IOException e) {
      e.printStackTrace();
    }
    hubUrl = properties.getProperty("hubUrl");
    baseUrl = properties.getProperty("baseUrl");
    browser = properties.getProperty("browser");
  }

  public String getBrowser() {
    return browser;
  }

  public String getHubUrl() {
    return hubUrl;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public String getConfigurationLocation() {
    return configurationLocation;
  }
}
