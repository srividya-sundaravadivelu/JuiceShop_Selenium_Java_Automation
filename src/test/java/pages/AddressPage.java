package pages;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddressPage {

	WebDriver driver;
	WebDriverWait wait;
	Actions actions;

	@FindBy(xpath = "//mat-label[text()='Country']/ancestor::div[contains(@class,'mat-form-field-infix')]//input")
	WebElement countryInput;

	@FindBy(xpath = "//mat-label[text()='Name']/ancestor::div[contains(@class,'mat-form-field-infix')]//input")
	WebElement nameInput;

	@FindBy(xpath = "//mat-label[text()='Mobile Number']/ancestor::div[contains(@class,'mat-form-field-infix')]//input")
	WebElement mobileNumberInput;

	@FindBy(xpath = "//mat-label[text()='ZIP Code']/ancestor::div[contains(@class,'mat-form-field-infix')]//input")
	WebElement zipCodeInput;

	@FindBy(id = "address")
	WebElement addressInput;

	@FindBy(xpath = "//mat-label[text()='City']/ancestor::div[contains(@class,'mat-form-field-infix')]//input")
	WebElement cityInput;

	@FindBy(xpath = "//mat-label[text()='State']/ancestor::div[contains(@class,'mat-form-field-infix')]//input")
	WebElement stateInput;

	@FindBy(id = "submitButton")
	WebElement submitButton;

	@FindBy(xpath = "//mat-cell //mat-radio-button")
	WebElement addressRadioButton;

	@FindBy(xpath = "//button[contains(@class,'btn-next')]")
	WebElement continueButton;

	public AddressPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		actions = new Actions(driver);
	}

	public void addNewAddress(String country, String name, int mobileNumber, String zipCode, String address,
			String city, String state) {

		countryInput.clear();
		countryInput.sendKeys(country);
		nameInput.clear();
		nameInput.sendKeys(name);
		mobileNumberInput.clear();
		mobileNumberInput.sendKeys(String.valueOf(mobileNumber));
		zipCodeInput.clear();
		zipCodeInput.sendKeys(zipCode);
		addressInput.clear();
		addressInput.sendKeys(address);
		cityInput.clear();
		cityInput.sendKeys(city);
		stateInput.clear();
		stateInput.sendKeys(state);	
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", submitButton);
		js.executeScript("arguments[0].click();", addressRadioButton);
		js.executeScript("arguments[0].click();", continueButton);
		
	}

}
