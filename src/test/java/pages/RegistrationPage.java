package pages;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage {
	WebDriver driver;
	@FindBy(id = "emailControl")
	WebElement emailField;

	@FindBy(id = "passwordControl")
	WebElement passwordField;

	@FindBy(id = "repeatPasswordControl")
	WebElement repeatPasswordField;

	@FindBy(name = "securityQuestion")
	WebElement securityQuestionField;

	@FindBy(className = "mat-option-text")
	List<WebElement> securityQuestionOptionsField;

	@FindBy(id = "securityAnswerControl")
	WebElement securityAnswerField;

	@FindBy(id = "registerButton")
	WebElement registerBtn;

	// @FindBy()

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void registerUser(String email, String password, String securityQuestion, String securityAnswer) {

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
		LoginPage loginPage = new LoginPage(driver);
		loginPage.newCustomerClick();
		emailField.clear();
		emailField.sendKeys(email);
		passwordField.clear();
		passwordField.sendKeys(password);
		repeatPasswordField.clear();
		repeatPasswordField.sendKeys(password);
		// securityQuestionField.click();
		javascriptExecutor.executeScript("arguments[0].click();", securityQuestionField);

		for (WebElement option : securityQuestionOptionsField) {
			if (option.getText().trim().equals(securityQuestion.trim())) {
				option.click();
				break;
			}
		}

		securityAnswerField.clear();
		securityAnswerField.sendKeys(securityAnswer);

		javascriptExecutor.executeScript("arguments[0].click();", registerBtn);
	}

	public void getSuccessText() {
		
	}

}