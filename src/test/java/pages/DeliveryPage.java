package pages;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;
import data.UserData;

public class DeliveryPage extends BasePage {
	WebDriver driver;
	WebDriverWait wait;	
	
	@FindBy(xpath = "//mat-cell[contains(text(),'One Day Delivery')]/ancestor::mat-row//mat-radio-button")
	WebElement oneDayDeliveryRadioButton;
	
	@FindBy(xpath = "//button[contains(@class,'nextButton')]")
	WebElement continueButton;	
	
	@FindBy(xpath = "//div[contains(@class,'addressCont')]//div[1]")
	WebElement nameDiv;
	
	@FindBy(xpath = "//div[contains(@class,'addressCont')]//div[2]")
	WebElement addressLineDiv;
	
	@FindBy(xpath = "//div[contains(@class,'addressCont')]//div[3]")
	WebElement countryDiv;
	
	@FindBy(xpath="//span[text()='Phone Number']/following-sibling::*")
	WebElement phoneNumberDiv;
	
	@FindBy(xpath = "//h1[text()='Delivery Address']")
	WebElement deliveryHeader;
	
	public DeliveryPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);		
	}
	
	public PaymentPage selectOneDayDelivery() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", oneDayDeliveryRadioButton);
		return continueClick();
	}
	
	private PaymentPage continueClick() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", continueButton);	
		return new PaymentPage(driver);
	}
	
	public String getName() {
		return nameDiv.getText();
		
	}
	
	public String getAddress() {
		return addressLineDiv.getText();
		
	}
	
	public String getCountry() {
		return countryDiv.getText();
		
	}
	
	public String getPhoneNumber() {
		return phoneNumberDiv.getText();
		
	}
	
	public boolean isDeliveryHeaderDisplayed() {
		try {	
	    	
	        return deliveryHeader.isDisplayed();
	    } catch (NoSuchElementException e) {		    	
	        return false;
	    }
	}

}