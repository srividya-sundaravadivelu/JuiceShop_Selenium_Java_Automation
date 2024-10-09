package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderHistoryPage {
WebDriver driver;
	
	@FindBy(xpath = "//mat-card-title[contains(text(),'Order History')]")
	WebElement orderHistoryHeader;
	
	public OrderHistoryPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean isOrderHistoryHeaderDisplayed() {
		try {	
	    	
	        return orderHistoryHeader.isDisplayed();
	    } catch (NoSuchElementException e) {		    	
	        return false;
	    }
	}

}

