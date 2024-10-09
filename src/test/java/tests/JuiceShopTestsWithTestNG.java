package tests;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.Assert;

import data.UserData;
import data.UserDataBuilder;
import pages.AddressPage;
import pages.CheckoutPage;
import pages.DeliveryPage;
import pages.LoginPage;
import pages.MainPage;
import pages.NavBar;
import pages.OrderCompletionPage;
import pages.OrderHistoryPage;
import pages.OrderSummary;
import pages.PaymentPage;
import pages.ProductPage;
import pages.RegistrationPage;
import pages.TrackOrderPage;
import listeners.RetryLoginAfterRegistrationListener;

@Listeners(RetryLoginAfterRegistrationListener.class)
public class JuiceShopTestsWithTestNG {

	WebDriver driver;
	MainPage mainPage;
	private boolean loginSuccess = false;
	UserData userData;

	@BeforeClass
	public void initializeTest() {
		final String websiteLink = "https://juice-shop.herokuapp.com/#/";
		// Set up ChromeDriver
		String projectPath = System.getProperty("user.dir");
		String driverPath = projectPath + "/src/test/resources/drivers/chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", driverPath);
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(3));

		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();

		driver.get(websiteLink);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		userData = UserDataBuilder.getUserData();
		
		mainPage = new MainPage(driver);
		mainPage.dismissPopups();
	}

	@Test (priority = 1)
	public void testLogin() {	
		NavBar navBar = new NavBar(driver);
		navBar.loginClick();

		LoginPage loginPage = new LoginPage(driver);
		navBar = loginPage.login(userData.getEmail(), userData.getPass());
		Assert.assertTrue(navBar.isLogoutLinkVisible());		
	}

	@Test(dependsOnMethods = "testLogin", alwaysRun = true)
	public void testRegisterUser() {
		if (!loginSuccess) {
			RegistrationPage registrationPage = new RegistrationPage(driver);
			LoginPage loginPage = registrationPage.registerUser(this.userData.getEmail(), this.userData.getPass(),
					"Name of your favorite pet?", "dog");
			
			Assert.assertTrue(loginPage.isLoginHeaderDisplayed());

		}
	}

	@Test(dependsOnMethods = "testLogin")
	public void testAddToCart() {
		ProductPage productPage = new ProductPage(driver);
		NavBar navBar = new NavBar(driver);
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		productPage.addProductToCart("Apple Juice");
		waitForDomStable();
		productPage.addProductToCart("Fruit Press");
		waitForDomStable();
		productPage.addProductToCart("Green Smoothie");
		waitForDomStable();
		navBar.navigateToYourBasket();
		Assert.assertTrue(checkoutPage.isProductInCart("Apple Juice"));
		Assert.assertTrue(checkoutPage.isProductInCart("Fruit Press"));
		Assert.assertTrue(checkoutPage.isProductInCart("Green Smoothie"));
		Assert.assertEquals(navBar.itemsInCart(), "3");
	}

	@Test(dependsOnMethods = "testAddToCart")
	public void testIncreaseAllQty() {
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		NavBar navBar = new NavBar(driver);
		waitForDomStable();
		checkoutPage.increaseAllQty();
		waitForDomStable();
		waitForDomStable();
		Assert.assertEquals(navBar.itemsInCart(), "6");
	}

	@Test(dependsOnMethods = "testIncreaseAllQty")
	public void testCheckout() {		
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		Assert.assertTrue(checkoutPage.getTotalPriceText().contains("187.93"));
		AddressPage addressPage = checkoutPage.checkout();			
		Assert.assertTrue(addressPage.isAddressHeaderDisplayed());
	}

	@Test(dependsOnMethods = "testCheckout")
	public void testAddNewAddress() {
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		DeliveryPage deliveryPage = new DeliveryPage(driver);
		deliveryPage = checkoutPage.addNewAddress(userData.getCountry(), userData.getName(), userData.getMobileNumber(),
				userData.getZipcode(), userData.getAddress(), userData.getCity(), userData.getState());
		
		
		Assert.assertEquals(deliveryPage.getName(), userData.getName());
		Assert.assertEquals(deliveryPage.getCountry(), userData.getCountry());
		Assert.assertTrue(deliveryPage.getAddress().contains(userData.getAddress()));
		Assert.assertTrue(deliveryPage.getAddress().contains(userData.getCity()));
		Assert.assertTrue(deliveryPage.getAddress().contains(userData.getZipcode()));
		Assert.assertEquals(deliveryPage.getCountry(), userData.getCountry());
//		Assert.assertEquals(deliveryPage.getPhoneNumber(), userData.getMobileNumber());
		Assert.assertTrue(deliveryPage.isDeliveryHeaderDisplayed());
	}

	@Test(dependsOnMethods = "testAddNewAddress")
	public void testSelectOneDayDelivery() {
		DeliveryPage deliveryPage = new DeliveryPage(driver);
		PaymentPage paymentPage = deliveryPage.selectOneDayDelivery();
		Assert.assertTrue(paymentPage.isPaymentHeaderDisplayed());
	}

	@Test(dependsOnMethods = "testSelectOneDayDelivery")
	public void testMakePayment() {
		PaymentPage paymentPage = new PaymentPage(driver);
		OrderSummary orderSummary = paymentPage.makePayment(userData.getName(), "4012888888881881", "6", "2080", "ABCDEFGHIJ");
		Assert.assertTrue(orderSummary.isOrderSummaryHeaderDisplayed());
	}

	@Test(dependsOnMethods = "testMakePayment")
	public void testPlaceOrderAndPay() {
		OrderSummary orderSummary = new OrderSummary(driver);
		OrderCompletionPage orderCompletionPage = orderSummary.placeOrderAndPay();
		Assert.assertTrue(orderCompletionPage.isOrderCompletionHeaderDisplayed());
	}

	@Test(dependsOnMethods = "testPlaceOrderAndPay")
	public void testViewOrderHistory() {
		NavBar navBar = new NavBar(driver);
		OrderHistoryPage orderHistoryPage = navBar.viewOrderHistory();
		Assert.assertTrue(orderHistoryPage.isOrderHistoryHeaderDisplayed());
	}

	@Test(dependsOnMethods = "testViewOrderHistory")
	public void testTrackOrder() {
		NavBar navBar = new NavBar(driver);
		TrackOrderPage trackOrderPage = navBar.trackOrder();
		Assert.assertTrue(trackOrderPage.isTrackOrderHeaderDisplayed());
	}

	@Test(dependsOnMethods = "testViewOrderHistory")
	public void testPrintOrderConfimation() {
		NavBar navBar = new NavBar(driver);
		navBar.printOrderConfimation();
	}

	@Test(dependsOnMethods = "testPrintOrderConfimation")
	public void testLogout() {
		NavBar navBar = new NavBar(driver);
		// Get all window handles
		List<String> tabs = new ArrayList<>(driver.getWindowHandles());

		// Switch to the original tab (assuming it's the first one, index 0)
		driver.switchTo().window(tabs.get(0));

		navBar = navBar.logout();
		Assert.assertTrue(navBar.isLoginLinkVisible());
	}

	public static void waitForDomStable() {
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
