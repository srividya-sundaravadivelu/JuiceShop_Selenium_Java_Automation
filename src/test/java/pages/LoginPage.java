package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	WebDriver driver;
	NavBar navBar;
	
	@FindBy(id = "email")
	WebElement emailField;

	@FindBy(id = "password")
	WebElement passwordField;

	@FindBy(id = "loginButton")
	WebElement loginBtn;

//	@FindBy(xpath = "//a[@routerlink='/register']")
//	WebElement registerLink;

	@FindBy(xpath = "//div[@id='newCustomerLink']//a")
	WebElement newCustomerLink;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		navBar = new NavBar(driver);
	}

	public boolean login(String email, String password) {
		emailField.clear();
		emailField.sendKeys(email);
		passwordField.clear();
		passwordField.sendKeys(password);
		//loginBtn.click();
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
		javascriptExecutor.executeScript("arguments[0].click();", loginBtn);
		return navBar.isLogoutLinkVisible();
	}
	
	

	public void newCustomerClick() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", newCustomerLink);
	}
}