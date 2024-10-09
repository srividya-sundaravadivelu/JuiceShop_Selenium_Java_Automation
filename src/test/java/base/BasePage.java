package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

//	public static WebDriver driver;
//
//	public void setDriver(WebDriver driver) {
//		BasePage.driver = driver;
//	}

	protected void set(WebElement webElement, String text) {
		webElement.clear();
		webElement.sendKeys(text);
	}

	protected void click(WebElement webElement) {
		webElement.click();
	}

}
