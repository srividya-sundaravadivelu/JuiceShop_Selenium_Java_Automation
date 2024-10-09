package tests;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;

import data.UserData;
import data.UserDataBuilder;
import pages.CheckoutPage;
import pages.DeliveryPage;
import pages.LoginPage;
import pages.MainPage;
import pages.NavBar;
import pages.OrderSummary;
import pages.PaymentPage;
import pages.ProductPage;
import pages.RegistrationPage;

public class JuiceShopTests {

	public static WebDriver initializeBrowser() {
		final String websiteLink = "https://juice-shop.herokuapp.com/#/";
		// Set up ChromeDriver
		String projectPath = System.getProperty("user.dir");
		String driverPath = projectPath + "/src/test/resources/drivers/chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", driverPath);
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(3));

		WebDriver driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();

		driver.get(websiteLink);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

		return driver;
	}

	public static void waitForDomStable() {
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		WebDriver driver = initializeBrowser();
		
		MainPage mainPage = new MainPage(driver);
		LoginPage loginPage = new LoginPage(driver);
		RegistrationPage registrationPage = new RegistrationPage(driver);
		ProductPage productPage = new ProductPage(driver);
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		DeliveryPage deliveryPage = new DeliveryPage(driver);
		PaymentPage paymentPage = new PaymentPage(driver);
		OrderSummary orderSummary= new OrderSummary(driver);
		NavBar navBar = new NavBar(driver);
		
		UserData userData = UserDataBuilder.getUserData();

		mainPage.dismissPopups();
		navBar.loginClick();
		boolean isloginSuccess = loginPage.login(userData.getEmail(), userData.getPass());

		if (!isloginSuccess) {
			registrationPage.registerUser(userData.getEmail(), userData.getPass(), "Name of your favorite pet?", "dog");
			loginPage.login(userData.getEmail(), userData.getPass());
		}

		productPage.addProductToCart("Apple Juice");
		productPage.addProductToCart("Carrot Juice");
		productPage.addProductToCart("Banana Juice");
		
		waitForDomStable();
		navBar.navigateToYourBasket();
		checkoutPage.increaseAllQty();
		checkoutPage.checkout();
		checkoutPage.addNewAddress(userData.getCountry(), userData.getName(), 
				userData.getMobileNumber(), userData.getZipcode(), userData.getAddress(), userData.getCity(), userData.getState());
		deliveryPage.selectOneDayDelivery();
		paymentPage.makePayment(userData.getName(), "4012888888881881", "6", "2080","ABCDEFGHIJ");
		orderSummary.placeOrderAndPay();
		
		navBar.viewOrderHistory();
		navBar.trackOrder();
		
		navBar.viewOrderHistory();
		navBar.printOrderConfimation();
		
		// Get all window handles
		List<String> tabs = new ArrayList<>(driver.getWindowHandles());
		
		// Switch to the original tab (assuming it's the first one, index 0)
		driver.switchTo().window(tabs.get(0));
		
		navBar.logout();
		
	}

}