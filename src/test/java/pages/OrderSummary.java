package pages;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderSummary {
	
	WebDriver driver;
	WebDriverWait wait;	
	JavascriptExecutor js;
	
	@FindBy(id="checkoutButton")
	WebElement placeOrderAndPayButton;
	
	public OrderSummary(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
	}
	
	public void placeOrderAndPay() {
		js.executeScript("arguments[0].click();", placeOrderAndPayButton);
	}

}