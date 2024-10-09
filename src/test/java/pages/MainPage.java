package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;

public class MainPage extends BasePage {
	WebDriver driver;
	NavBar navBar;
	
	@FindBy(xpath = "//a[contains(@class,'cc-btn')]")
	WebElement meWantThisLink;

	@FindBy(xpath = "//button[contains(@class,'close-dialog')]")
	WebElement dismissBtn;
	
	public MainPage(WebDriver driver) {
		 this.driver = driver;
         PageFactory.initElements(driver, this);
         navBar = new NavBar(driver);
	 }
	
	
	public void dismissPopups () {
        Actions action = new Actions (driver);
        meWantThisLink.click();        
        action.moveToElement(dismissBtn);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", dismissBtn);

    }

}