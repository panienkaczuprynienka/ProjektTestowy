package TestyPOM;

import Drivers.DriverFactory;
import PageObjects.CartPage;
import PageObjects.CheckoutPage;
import PageObjects.OrderReceivedPage;
import PageObjects.ProductPage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaymentTest extends TestBase {

  private String name = "Ola";
  private String lastname = "Nowak";
  private String countryCode = "BE";
  private String address = "Wielicka 2/15";
  private String postalCode = "80-001";
  private String city = "Sopot";
  private String phoneNumber = "6666666";
  private String email = "test1@testelka.pl";
  private String cardNumber = "4242424242424242";
  private String expirationDate = "0530";
  private String cvcCode = "123";

  @Test
  public void buyWithoutAccountTest() {
    String productUrl = baseUrl + "/product/egipt-el-gouna/";
    ProductPage productPage = new ProductPage(driver).goTo(productUrl);
    productPage.footer.closeDemoNotice();
    CartPage cartPage = productPage.addToCart().viewCart();
    CheckoutPage checkoutPage = cartPage.goToCheckout();


    OrderReceivedPage orderReceivedPage = checkoutPage.typeName(name)
            .typeLastName(lastname)
            .chooseCountry(countryCode)
            .typeAddress(address)
            .typePostalCode(postalCode)
            .typeCity(city)
            .typePhone(phoneNumber)
            .typeEmail(email)
            .typeCardNumber(cardNumber)
            .typeCardExpirationDate(expirationDate)
            .typeCvcCode(cvcCode)
            .selectAcceptTerms()
            .order();
    boolean isOrderSuccessful = orderReceivedPage.isOrderSuccessful();
    Assertions.assertThat(isOrderSuccessful)
            .overridingErrorMessage("No order successful message found.")
            .isTrue();

  }
}
