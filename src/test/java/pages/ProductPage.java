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

public class ProductPage {

	private WebDriver driver;
	private WebDriverWait wait;
	Actions actions;	
	
	@FindBy(xpath = "//span[contains(@class,'mat-simple-snack-bar-content')]")
	WebElement addToCartSuccessText;

	public ProductPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		actions = new Actions(driver);
		PageFactory.initElements(driver, this);
	}
	

	public void addProductToCart(String productName) {
		boolean productAdded = false;

		while (!productAdded) {

			List<WebElement> products = driver.findElements(By.xpath("//div[contains(text(),'" + productName + "')]"));

			if (!products.isEmpty()) {
				// Product found, now click "Add to Cart" button
				WebElement addToCartButton = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'" + productName
								+ "')]/ancestor::div[contains(@class,'mat-grid-tile-content')]//button")));

				actions.scrollToElement(addToCartButton).build().perform();

				JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
				javascriptExecutor.executeScript("arguments[0].click();", addToCartButton);
				productAdded = true;
			} else {
				// Product not found, check if there is a next page
				WebElement nextButton = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Next page']")));
				if (nextButton.isEnabled()) {

					actions.moveToElement(nextButton).build().perform();
					JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
					javascriptExecutor.executeScript("arguments[0].click();", nextButton);

					// Wait for the next page to load
					wait.until(ExpectedConditions.stalenessOf(nextButton)); // Wait for the next page to load completely
				} else {
					System.out.println("Product not found in any page.");
					break;
				}
			}
		}
		
	}
	
	public String getSuccessText() {
		try {
			actions.moveToElement(addToCartSuccessText).build().perform();
			WebElement successText = wait.until(ExpectedConditions.elementToBeClickable(addToCartSuccessText));
			return successText.getText();
		}
		catch(NoSuchElementException ex)
		{
			return "";
		}
		
	}

}