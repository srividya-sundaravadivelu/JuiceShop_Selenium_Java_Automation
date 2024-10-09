package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;

public class OrderCompletionPage extends BasePage {
	
	WebDriver driver;
	
	@FindBy(xpath = "//h1[text()='Thank you for your purchase!']")
	WebElement orderCompletionHeader;
	
	public OrderCompletionPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean isOrderCompletionHeaderDisplayed() {
		try {	
	    	
	        return orderCompletionHeader.isDisplayed();
	    } catch (NoSuchElementException e) {		    	
	        return false;
	    }
	}

}
