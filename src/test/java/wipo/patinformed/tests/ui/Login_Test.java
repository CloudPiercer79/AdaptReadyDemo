package wipo.patinformed.tests.ui;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import wipo.patinformed.pages.GetDriver;
import wipo.patinformed.utils.LoadProperties;

public class Login_Test {

	public static WebDriver driver;

	@BeforeSuite
	public void launchingWebBrowser() throws IOException {

		/* Creating an Instance of chrome browser if the browser selected is chrome */
		String browser = LoadProperties.getInstance().getProperty("browser");

		if (browser.equals("chrome")) {
			ChromeOptions handlingSSL = new ChromeOptions();
			handlingSSL.setAcceptInsecureCerts(true);
			Login_Test.driver = new ChromeDriver(handlingSSL);
			System.out.println("Selected browser is : " + browser);
		}
		/* Creating an Instance of Edge browser if the browser selected is edge */
		else if (browser.equals("edge")) {
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.setAcceptInsecureCerts(true);
			Login_Test.driver = new EdgeDriver(edgeOptions);
			System.out.println("Selected browser is : " + browser);
		}
		/* Creating an Instance of safari browser if the browser selected is safari */
		else if (browser.equals("safari")) {
			Login_Test.driver = new SafariDriver();
			driver.get("https://revoked.badssl.com");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("CertificateWarningController.visitInsecureWebsiteWithTemporaryBypass()");
			System.out.println("Selected browser is : " + browser);
		}
		/*
		 * Exit the code of the browser definer in config.properties file is incorrect
		 */
		else {
			System.out.println("[Error] : Please enter valid browser name in conig.properties");
			System.exit(0);
		}

		GetDriver.driver = driver;

	}

	@Test(priority = 0)
	public void hittingAppURL() throws IOException {
		/* maximize the window */
		driver.manage().window().maximize();

		/* static wait for the page to load completely */
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		/* hitting application URL */
		driver.get(LoadProperties.getInstance().getProperty("appURL"));
	}

}
