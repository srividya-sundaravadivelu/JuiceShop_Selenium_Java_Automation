package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TrackOrderPage {
WebDriver driver;
	
	@FindBy(xpath = "//h1//span[text()='Search Results']")
	WebElement trackOrderHeader;
	
	public TrackOrderPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean isTrackOrderHeaderDisplayed() {
		try {	
	    	
	        return trackOrderHeader.isDisplayed();
	    } catch (NoSuchElementException e) {		    	
	        return false;
	    }
	}

}


