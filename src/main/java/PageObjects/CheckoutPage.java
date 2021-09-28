package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class CheckoutPage extends BasePage{
  WebDriverWait wait;
  private By firstNameFieldLocator = By.cssSelector("#billing_first_name");
  private By lastNameFieldLocator = By.cssSelector("#billing_last_name");
  private By countryCodeArrowLocator = By.cssSelector(".select2-selection__arrow");
  private String countryCodeCssSelector = "li[id*='-<country_code>']";
  private By addressFieldLocator = By.cssSelector("#billing_address_1");
  private By postalCodeFieldLocator = By.cssSelector("#billing_postcode");
  private By cityFieldLocator = By.cssSelector("#billing_city");
  private By phoneFieldLocator = By.cssSelector("#billing_phone");
  private By emailFieldLocator = By.cssSelector("#billing_email");
  private By loadingIconLocator = By.cssSelector(".blockOverlay");
  private By cardNumberFrameLocator = By.cssSelector("[name='__privateStripeFrame8']");
  private By cardNumberFieldLocator = By.cssSelector("[name='cardnumber']");
  private By expirationDateFrameLocator = By.cssSelector("[name='__privateStripeFrame9']");
  private By expirationDateFieldLocator = By.cssSelector("[name='exp-date']");
  private By cvcFrameLocator = By.cssSelector("[name='__privateStripeFrame10']");
  private By cvcFieldLocator = By.cssSelector("[name='cvc']");
  private By termsCheckboxLocator = By.cssSelector("#terms");
  private By summaryOrderNumberLocator = By.cssSelector(".order>strong");
  private By orderButtonLocator = By.cssSelector("#place_order");
  public CheckoutPage(WebDriver driver) {
    super(driver);
    wait = new WebDriverWait(driver, 5);
  }
  public CheckoutPage typeName(String name) {
    wait.until(ExpectedConditions.elementToBeClickable(firstNameFieldLocator)).sendKeys(name);
    return this;
  }
  public CheckoutPage typeLastName(String lastName) {
    wait.until(ExpectedConditions.elementToBeClickable(lastNameFieldLocator)).sendKeys(lastName);
    return this;
  }
  public CheckoutPage chooseCountry(String countryCode) {
    wait.until(ExpectedConditions.elementToBeClickable(countryCodeArrowLocator)).click();
    By countryCodeLocator = By.cssSelector(countryCodeCssSelector.replace("<country_code>", countryCode));
    wait.until(ExpectedConditions.elementToBeClickable(countryCodeLocator)).click();
    return this;
  }
  public CheckoutPage typeAddress(String address) {
    wait.until(ExpectedConditions.elementToBeClickable(addressFieldLocator)).click();
    wait.until(ExpectedConditions.elementToBeClickable(addressFieldLocator)).sendKeys(address);
    return this;
  }
  public CheckoutPage typePostalCode(String postalCode) {
    wait.until(ExpectedConditions.elementToBeClickable(postalCodeFieldLocator)).sendKeys(postalCode);
    return this;
  }
  public CheckoutPage typeCity(String city) {
    wait.until(ExpectedConditions.elementToBeClickable(cityFieldLocator)).sendKeys("Sopot");
    return this;
  }
  public CheckoutPage typePhone(String phone) {
    wait.until(ExpectedConditions.elementToBeClickable(phoneFieldLocator)).sendKeys(phone);
    return this;
  }
  public CheckoutPage typeEmail(String emailAddress) {
    wait.until(ExpectedConditions.elementToBeClickable(emailFieldLocator)).sendKeys(emailAddress);
    return this;
  }
  public CheckoutPage typeCardNumber(String cardNumber) {
    wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIconLocator));
    WebElement cardNumberElement = findElementInFrame(cardNumberFrameLocator, cardNumberFieldLocator);
    slowType(cardNumberElement, cardNumber);
    driver.switchTo().defaultContent();
    return this;
  }
  public CheckoutPage typeCardExpirationDate(String expirationDate) {
    wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIconLocator));
    WebElement expirationDateElement = findElementInFrame(expirationDateFrameLocator, expirationDateFieldLocator);
    slowType(expirationDateElement, expirationDate);
    driver.switchTo().defaultContent();
    return this;
  }
  public CheckoutPage typeCvcCode(String cvcCode) {
    wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIconLocator));
    WebElement cvcElement = findElementInFrame(cvcFrameLocator, cvcFieldLocator);
    slowType(cvcElement,cvcCode);
    driver.switchTo().defaultContent();
    return this;
  }
  private WebElement findElementInFrame(By frameLocator, By elementLocator){
    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    return wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
  }
  private void slowType(WebElement element, String text) {
    for (int i = 0; i < text.length(); i++) {
      element.sendKeys(Character.toString(text.charAt(i)));
    }
  }
  public CheckoutPage selectAcceptTerms() {
    wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIconLocator));
    driver.findElement(termsCheckboxLocator).click();
    return this;
  }
  public OrderReceivedPage order() {
    driver.findElement(orderButtonLocator).click();
    return new OrderReceivedPage(driver);
  }
}