package pages;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
	WebDriver driver;
	NavBar navBar;
//	@FindBy(id = "navbarAccount")
//	WebElement accountLink;

//	@FindBy(id = "navbarLoginButton")
//	WebElement loginLink;
	
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
//        navBar.login();
//        accountLink.click();
//        loginLink.click();
    }

}