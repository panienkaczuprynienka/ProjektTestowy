package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoryPage extends BasePage {

  public FooterPage footer;

  public CategoryPage(WebDriver driver) {
    super(driver);
    footer = new FooterPage(driver);
    wait = new WebDriverWait(driver, 5);
  }

  private WebDriverWait wait;

  private By viewCartBtnLocator = By.cssSelector(".added_to_cart");
  private String addToCartBtnSelector = ".post-<product_id>>.add_to_cart_button";

  public CategoryPage goTo(String url) {
    driver.navigate().to(url);
    return this;
  }

  public CategoryPage addToCart(String productId) {
    By addToCartButton = By.cssSelector(addToCartBtnSelector.replace("<product_id>", productId));
    driver.findElement(addToCartButton).click();
    wait.until(ExpectedConditions.attributeContains(addToCartButton, "class", "added"));
    return this;
  }

  public CartPage viewCart() {
    wait.until(ExpectedConditions.elementToBeClickable(viewCartBtnLocator)).click();
    return new CartPage(driver);
  }
}
