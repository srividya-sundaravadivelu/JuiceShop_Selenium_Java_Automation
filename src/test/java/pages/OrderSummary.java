package pages;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;

public class OrderSummary extends BasePage {
	
	WebDriver driver;
	WebDriverWait wait;	
	JavascriptExecutor js;
	
	@FindBy(id="checkoutButton")
	WebElement placeOrderAndPayButton;
	
	@FindBy(xpath = "//div[text()='Order Summary']")
	WebElement orderSummaryHeader;
	
	public OrderSummary(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
	}
	
	public OrderCompletionPage placeOrderAndPay() {
		js.executeScript("arguments[0].click();", placeOrderAndPayButton);
		return new OrderCompletionPage(driver);
	}
	
	public boolean isOrderSummaryHeaderDisplayed() {
		try {	
	    	
	        return orderSummaryHeader.isDisplayed();
	    } catch (NoSuchElementException e) {		    	
	        return false;
	    }
	}

}