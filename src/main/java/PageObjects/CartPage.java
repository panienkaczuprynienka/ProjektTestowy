package PageObjects;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage {

  private WebDriverWait wait;

  public CartPage(WebDriver driver) {
    super(driver);
    wait = new WebDriverWait(driver, 15);
  }

  private By shopTableLocator = By.cssSelector("form>.shop_table");
  private By productQuantityFieldLocator = By.cssSelector(".quantity .input-text");
  private String removeProductButtonCssSelector = "a[data-product_id='<product_id>']";
  private By removeOneInCartProductBtnLocator = By.cssSelector(".remove");
  private By cartItems = By.cssSelector(".cart_item");
  By updateCartButton = By.cssSelector("[name='update_cart']");
  By waitOverlay = By.cssSelector(".blockOverlay");
  By checkoutButtonLocator = By.cssSelector(".checkout-button");


  public int getNumberOfSpecificProduct(String productId) {
    waitForShopTable();
    By removeProductButton = By.cssSelector(removeProductButtonCssSelector.replace("<product_id>", productId));
    return driver.findElements(removeProductButton).size();
  }

  public int getQuantityOfProduct() {
    waitForShopTable();
    return Integer.parseInt(driver.findElement(productQuantityFieldLocator).getAttribute("value"));
  }

  public boolean isProductInCart(String productId) {
    By removeProductButton = By.cssSelector(removeProductButtonCssSelector.replace("<product_id>", productId));
    int productRecords = driver.findElements(removeProductButton).size();
    boolean presenceOfProduct = false;
    if (productRecords == 1) {
      presenceOfProduct = true;
    } else if (productRecords > 1) {
      throw new IllegalArgumentException("There is more than one record for the product in cart.");
    }
    return presenceOfProduct;
  }


  public int getNumberOfProduct() {
    waitForShopTable();
    return driver.findElements(cartItems).size();
  }


  public void waitForShopTable() {
    WebDriverWait wait = new WebDriverWait(driver, 7);
    wait.until(ExpectedConditions.presenceOfElementLocated(shopTableLocator));
  }

  public CartPage updateCart() {
    WebElement updateButton = driver.findElement(updateCartButton);
    wait.until(ExpectedConditions.elementToBeClickable(updateButton));
    updateButton.click();
    waitForOverlayDisappear();
    return this;
  }

  public CartPage changeQuantityOfProduct(int quantityInt) {
    WebElement quantityElem = driver.findElement(productQuantityFieldLocator);
    quantityElem.clear();
    quantityElem.sendKeys(String.valueOf(quantityInt));
    //quantityElem.submit(); //niepotrzebne jak klikam później na przycisk zupdejtuj koszyk
    return this;
  }

  public void checkExpectedQuantity(int expectedQuantity) {
    String quantityString = driver.findElement(By.cssSelector("div.quantity>input")).getAttribute("value");
    int quantity = Integer.parseInt(quantityString);
    Assertions.assertThat(quantity)
            .overridingErrorMessage("Quantity of the product is not what expected. Expected: %s, but was  %s",
                    expectedQuantity, quantity)
            .isEqualTo(expectedQuantity);
  }

  public void removeProductFromCart() {
    driver.findElement(removeOneInCartProductBtnLocator).click();
    waitForOverlayDisappear();
    int numberOfEmptyCartMessages = driver.findElements(By.cssSelector("p.cart-empty")).size();

    Assertions.assertThat(1)
            .overridingErrorMessage("One message about empty cart was expected, but found %s", numberOfEmptyCartMessages)
            .isEqualTo(numberOfEmptyCartMessages);
  }

   private void waitForOverlayDisappear(){
     WebElement waitBlockingOverlay = driver.findElement(waitOverlay);
     wait.until(ExpectedConditions.invisibilityOf(waitBlockingOverlay));
  }

  private boolean isCartEmpty(){
    int shopTableElements = driver.findElements(shopTableLocator).size();
    if (shopTableElements == 1){
      return false;
    } else if (shopTableElements == 0){
      return true;
    } else {
      throw new IllegalArgumentException("Wrong number of shop table elements: there can be only one or none");
    }
  }

  public CheckoutPage goToCheckout() {
    driver.findElement(checkoutButtonLocator).click();
    return new CheckoutPage(driver);
  }

}
