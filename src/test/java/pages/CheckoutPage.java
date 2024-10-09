package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {

	WebDriver driver;
	WebDriverWait wait;	
	AddressPage addressPage;
	
	@FindBy(id = "checkoutButton")
	WebElement checkoutButton;

	@FindBy(xpath = "//button[contains(@class,'btn-new-address')]")
	WebElement addNewAddressButton;
	
	@FindBy(id = "price")
	WebElement totalPrice;

	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);		
		addressPage = new AddressPage(driver);
	}

	public void checkout() {
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", checkoutButton);
	}

	private void addNewAddressClick() {	
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", addNewAddressButton);
	}

	public void increaseAllQty() {

		List<WebElement> increaseQtyButtons = driver
				.findElements(By.xpath("//*[contains(@class, 'fa-plus-square')]/ancestor::button"));

		for (WebElement btn : increaseQtyButtons) {

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", btn);

		}
	}
	
	public boolean isProductInCart(String productName)
	{
		try {
			
			WebElement product = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-cell[contains(text(),'" + productName + "')]")));
			return product.isDisplayed();
			
		}
		catch(NoSuchElementException ex)
		{
			return false;
		}
	}
	
	public String getTotalPriceText() {
		return totalPrice.getText();
	}

	public void addNewAddress(String country, String name, int mobileNumber, String zipCode, String address,
			String city, String state) {
		addNewAddressClick();
		addressPage.addNewAddress(country, name, mobileNumber, zipCode, address, city, state);

	}

}