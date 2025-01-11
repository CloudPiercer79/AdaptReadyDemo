package wipo.patinformed.tests.ui;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import wipo.patinformed.pages.GetDriver;

public class Logout_and_CloseBrowserTest {
	public WebDriver driver;
	
	@BeforeClass
	public void launchingWebBrowser() throws IOException {
		driver = GetDriver.driver;
		//patentSearch = new PatentSearchPage(driver);

	} 

	@Test(priority = 0)
	public void logout() {
		// Enter logout code here -- //
	}

	@Test(priority = 1)
	public void closeAllWindows() {
		driver.quit();
	}

}
