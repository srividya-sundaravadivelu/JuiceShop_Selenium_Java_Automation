package tests.base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import data.UserData;
import data.UserDataBuilder;
import pages.MainPage;

public class BaseTest {
	
	protected WebDriver driver;
	protected MainPage mainPage;
	protected UserData userData;
	
	@BeforeClass
	public void setUp() {
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
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	@AfterMethod
	public void takeFailedResultScreenshot(ITestResult testResult) {
		if (ITestResult.FAILURE == testResult.getStatus()) {
			TakesScreenshot screenshot = (TakesScreenshot) driver;
			File source = screenshot.getScreenshotAs(OutputType.FILE);
			File destination = new File(System.getProperty("user.dir") + "/resources/screenshots/("
					+ java.time.LocalDate.now() + ") " + testResult.getName() + ".png");
			try {
				FileHandler.copy(source, destination);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			System.out.println("Screenshot Located At " + destination);
		}
	}

	public static void waitForDomStable() {
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
