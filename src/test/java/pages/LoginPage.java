package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;

public class LoginPage extends BasePage {
	WebDriver driver;
	NavBar navBar;
	
	@FindBy(id = "email")
	WebElement emailField;

	@FindBy(id = "password")
	WebElement passwordField;

	@FindBy(id = "loginButton")
	WebElement loginBtn;

	@FindBy(xpath = "//div[@id='newCustomerLink']//a")
	WebElement newCustomerLink;
	
	@FindBy(xpath = "//h1[text()='Login']")
	WebElement loginHeader;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		navBar = new NavBar(driver);
	}

	public NavBar login(String email, String password) {
		set(emailField,email);
		set(passwordField,password);		
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
		javascriptExecutor.executeScript("arguments[0].click();", loginBtn);
		return new NavBar(driver);
	}
	

	public void newCustomerClick() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", newCustomerLink);
	}
	
	public boolean isLoginHeaderDisplayed() {
		try {	
	    	
	        return loginHeader.isDisplayed();
	    } catch (NoSuchElementException e) {		    	
	        return false;
	    }
	}
}