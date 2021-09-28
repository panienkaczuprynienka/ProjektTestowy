package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends BasePage{
  public HeaderPage header;
  public FooterPage footer;
  private WebDriverWait wait;
  public ProductPage(WebDriver driver) {
    super(driver);
    header = new HeaderPage(driver);
    footer = new FooterPage(driver);
    wait = new WebDriverWait(driver, 7);
  }
  private By addToCartLocator = By.cssSelector("button[name='add-to-cart']");
  private By viewCartLocator = By.cssSelector(".woocommerce-message>.button");
  private By productQuantityFieldLocator = By.cssSelector(".input-text");

  public ProductPage goTo(String productUrl) {
    driver.navigate().to(productUrl);
    return new ProductPage(driver);
  }

  public ProductPage addToCart() {
    WebElement addToCartBtn = driver.findElement(addToCartLocator);
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartBtn);
    addToCartBtn.click();
    wait.until(ExpectedConditions.elementToBeClickable(viewCartLocator));
    return this;
  }

  public ProductPage addToCartMultipleTimes(int quantityInt) {
    setQuantityOfProduct(quantityInt);
    WebElement addToCartBtn = driver.findElement(addToCartLocator);
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartBtn);
    addToCartBtn.click();
    wait.until(ExpectedConditions.elementToBeClickable(viewCartLocator));
    return this;
  }

  public void setQuantityOfProduct(int quantityInt){
    WebElement quantityElem = driver.findElement(productQuantityFieldLocator);
    quantityElem.clear();
    quantityElem.sendKeys(String.valueOf(quantityInt));
    quantityElem.submit();
  }

  public CartPage viewCart() {
    wait.until(ExpectedConditions.elementToBeClickable(viewCartLocator)).click();
    return new CartPage(driver);
  }


}
