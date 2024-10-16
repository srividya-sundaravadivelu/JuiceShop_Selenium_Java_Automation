
package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;

public class PaymentPage extends BasePage {

	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	Actions actions;
	
	// Card Details Section
	@FindBy(xpath = "//mat-panel-title[contains(text(),'Add new card')]/ancestor::mat-expansion-panel//span[contains(@class,'mat-expansion-indicator')]")
	WebElement addNewCardPanel;	

	@FindBy(xpath = "//mat-label[text()='Name']/ancestor::div[contains(@class,'mat-form-field-infix')]//input")
	WebElement nameInput;

	@FindBy(xpath = "//mat-label[text()='Card Number']/ancestor::div[contains(@class,'mat-form-field-infix')]//input")
	WebElement cardNumberInput;

	@FindBy(xpath = "//mat-label[text()='Expiry Month']/ancestor::div[contains(@class,'mat-form-field-infix')]//select")
	WebElement expiryMonthSelect;

	@FindBy(xpath = "//mat-label[text()='Expiry Year']/ancestor::div[contains(@class,'mat-form-field-infix')]//select")
	WebElement expiryYearSelect;

	@FindBy(id = "submitButton")
	WebElement submitButton;

//	@FindBy(xpath = "//mat-row[1]//input[contains(@class,'mat-radio-input')]")
	@FindBy(xpath = "//mat-row[1]//span[@class='mat-radio-outer-circle']")
	WebElement cardDetailsRadioButton;
	
	@FindBy(xpath = "//button[contains(@class,'nextButton')]")
	WebElement continueButton;
	
	@FindBy(xpath = "//h1[text()='My Payment Options']")
	WebElement paymentHeader;
	
	// Coupon Section
	@FindBy(xpath = "//mat-panel-title[contains(text(),'Add a coupon')]/ancestor::mat-expansion-panel//span[contains(@class,'mat-expansion-indicator')]")
	WebElement addCouponPanel;
	
	@FindBy(xpath = "//mat-label[text()='Coupon']/ancestor::div[contains(@class,'mat-form-field-infix')]//input")
	WebElement couponInput;
	
	@FindBy(id = "applyCouponButton")
	WebElement applyCouponButton;
	
	@FindBy(xpath = "//div[contains(@class,'error')]")
	WebElement couponError;
	

	public PaymentPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
		actions = new Actions(driver);
	}

	private void addNewCard(String name, String cardNumber, String expiryMonth, String expiryYear) {
//		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", addNewCardPanel);

		set(nameInput,name);
		set(cardNumberInput,cardNumber);

		Select expiryMonthDropDown = new Select(expiryMonthSelect);
		expiryMonthDropDown.selectByValue(expiryMonth);

		Select expiryYearDropDown = new Select(expiryYearSelect);
		expiryYearDropDown.selectByValue(expiryYear);

		js.executeScript("arguments[0].click();", submitButton);

	}
	
	private String addCoupon(String couponCode) {
		js.executeScript("arguments[0].click();", addCouponPanel);
		set(couponInput,couponCode);
		js.executeScript("arguments[0].click();", applyCouponButton);
		
		List<WebElement> errors = driver.findElements(By.xpath("//div[contains(@class,'error')]"));
		if(!errors.isEmpty())
			return errors.getFirst().getText();
		return "";
		
	}

	public OrderSummary makePayment(String name, String cardNumber, String expiryMonth, String expiryYear, String couponCode) {

		addNewCard(name, cardNumber, expiryMonth, expiryYear);
		String errorText = addCoupon(couponCode);
		System.out.println(errorText);
		js.executeScript("arguments[0].click();", wait.until(ExpectedConditions.elementToBeClickable(cardDetailsRadioButton)));
		return continueButtonClick();
	}
	
	public OrderSummary continueButtonClick() {
		js.executeScript("arguments[0].click();", wait.until(ExpectedConditions.elementToBeClickable(continueButton)));
		return new OrderSummary(driver);
	}
	
	public boolean isPaymentHeaderDisplayed() {
		try {	
	    	
	        return paymentHeader.isDisplayed();
	    } catch (NoSuchElementException e) {		    	
	        return false;
	    }
	}

}