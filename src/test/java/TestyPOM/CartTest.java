package TestyPOM;

import PageObjects.CategoryPage;
import PageObjects.ProductPage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartTest extends TestBase {

  String[] productPages = {"/egipt-el-gouna/", "/wspinaczka-via-ferraty/", "/wspinaczka-island-peak/",
          "/fuerteventura-sotavento/", "/grecja-limnos/", "/windsurfing-w-karpathos/",
          "/wyspy-zielonego-przyladka-sal/", "/wakacje-z-yoga-w-kraju-kwitnacej-wisni/",
          "/wczasy-relaksacyjne-z-yoga-w-toskanii/", "/yoga-i-pilates-w-hiszpanii/"};


  @Test
  public void addToCartFromProductPageTest() {
    String productId = "386";
    String productUrl = baseUrl + "/product/egipt-el-gouna/";
    ProductPage productPage = new ProductPage(driver);
    boolean isProductInCart = productPage.goTo(productUrl).addToCart().viewCart().isProductInCart(productId);

    Assertions.assertThat(isProductInCart)
            .overridingErrorMessage("Remove button was not found for a product with id=%s.", productId)
            .isTrue();
  }

  @Test
  public void addToCartFromCategoryPageTest() {
    String productId = "386";
    String categoryUrl = baseUrl + "/product-category/windsurfing/";
    CategoryPage categoryPage = new CategoryPage(driver);
    boolean isProductInCart = categoryPage.goTo(categoryUrl).addToCart(productId).viewCart().isProductInCart(productId);

    Assertions.assertThat(isProductInCart)
            .overridingErrorMessage("Remove button was not found for a product with id=%s.", productId)
            .isTrue();
  }

  @Test
  public void addOneProductTenTimesTest() {
    String productUrl = baseUrl + "/product/egipt-el-gouna/";
    ProductPage productPage = new ProductPage(driver);
    int numberOfProducts = productPage.goTo(productUrl).addToCartMultipleTimes(10).viewCart().getQuantityOfProduct();

    Assertions.assertThat(numberOfProducts)
            .overridingErrorMessage("Expected number is 10, actual - %s", numberOfProducts)
            .isEqualTo(10);
  }

  @Test
  public void addTenProductsToCartTest() {
    ProductPage productPage = new ProductPage(driver);
    for (String product : productPages) {
      productPage.goTo(baseUrl + "/product" + product).addToCart();
    }
    int numberOfItems = productPage.header.viewCart().getNumberOfProduct();

    assertEquals(10, numberOfItems,
            "Number of items in the cart is not correct. Expected: 10, but was: " + numberOfItems);
  }

  @Test
  public void changeNumberOfProductsTest() {
    int numberOfProducts = 8;
    ProductPage productPage = new ProductPage(driver);
    productPage.goTo(baseUrl + "/product/egipt-el-gouna/").addToCart().viewCart()
            .changeQuantityOfProduct(numberOfProducts).updateCart().checkExpectedQuantity(numberOfProducts);
  }

  @Test
  public void removePositionFromCartTest() {
    ProductPage productPage = new ProductPage(driver);
    productPage.goTo(baseUrl + "/product/egipt-el-gouna/").addToCart().viewCart().removeProductFromCart();
  }
}
