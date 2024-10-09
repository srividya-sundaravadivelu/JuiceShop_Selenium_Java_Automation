
package pages;

import java.time.Duration;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;

public class NavBar extends BasePage {
	WebDriver driver;
	WebDriverWait wait;	
	JavascriptExecutor js;
	Actions actions;
	
	@FindBy(id="navbarAccount")
	WebElement accountButton;
	
	@FindBy(xpath = "//div[contains(@class,'mat-menu-content')]//span[text()=' Orders & Payment ']/parent::button")
	WebElement ordersAndPaymentButton;
	
	@FindBy(xpath = "//span[contains(text(),'Order History')]/parent::button")
	WebElement orderHistoryButton;
	
	@FindBy(xpath = "//div[@class='heading']//button[@aria-label='Print order confirmation']")
	WebElement printOrderButton;
	
	@FindBy(xpath = "//div[@class='heading']//button[@aria-label='Track Your Order']")
	WebElement trackOrderButton;
	
	@FindBy(id = "navbarLogoutButton")
	WebElement logoutButton;
	
	@FindBy(id = "navbarLoginButton")
	WebElement loginButton;
	
	@FindBy(xpath = "//button[contains(@class,'mat-focus-indicator buttons mat-button mat-button-base ng-star-inserted')]")
	WebElement yourBasketButton;
	
	@FindBy(xpath = "//span[contains(@class,'warn-notification')]")
	WebElement itemsInCart;
	
	public NavBar(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
		actions = new Actions(driver);
	}
	
	public OrderHistoryPage viewOrderHistory() {
		accountButtonClick();
		js.executeScript("arguments[0].click();", ordersAndPaymentButton);
		return orderHistoryButtonClick();
	}
	
	public OrderHistoryPage orderHistoryButtonClick() {
		js.executeScript("arguments[0].click();", orderHistoryButton);	
		return new OrderHistoryPage(driver);
	}
	
	public boolean isLogoutLinkVisible() {
	    try {	
	    	accountButtonClick();
	    	System.out.println("is logout displayed" + logoutButton.isDisplayed());
	        return logoutButton.isDisplayed();
	    } catch (NoSuchElementException e) {	
	    	System.out.println("Caught Exception");
	        return false;
	    }
	}
	
	public boolean isLoginLinkVisible() {
		try {	
	    	accountButtonClick();
	        return loginButton.isDisplayed();
	    } catch (NoSuchElementException e) {	        
	        return false;
	    }
	}
	
	public void loginClick() {
		accountButtonClick();
		js.executeScript("arguments[0].click();", loginButton);
	}
	
	public NavBar logout() {
		accountButtonClick();
		js.executeScript("arguments[0].click();", logoutButton);
		return new NavBar(driver);
	}
	
	private void accountButtonClick() {
		actions.moveToElement(accountButton).build().perform();
		js.executeScript("arguments[0].click();", accountButton);
	}
	
	public void printOrderConfimation() {
		js.executeScript("arguments[0].click();", printOrderButton);
	}
	
	public TrackOrderPage trackOrder() {
		js.executeScript("arguments[0].click();", trackOrderButton);
		return new TrackOrderPage(driver);
	}
	
	public void navigateToYourBasket() {
		js.executeScript("arguments[0].click();", yourBasketButton);
	}
	
	public String itemsInCart() {
		actions.moveToElement(itemsInCart).build().perform();
		WebElement itemsInCartElement = wait
				.until(ExpectedConditions.elementToBeClickable(itemsInCart));
		return itemsInCartElement.getText();
	}

}