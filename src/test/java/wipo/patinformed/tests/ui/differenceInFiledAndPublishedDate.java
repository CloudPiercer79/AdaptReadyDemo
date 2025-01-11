package wipo.patinformed.tests.ui;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import wipo.patinformed.pages.GetDriver;
import wipo.patinformed.pages.PatentSearchPage;
import wipo.patinformed.utils.LoadProperties;

public class differenceInFiledAndPublishedDate {
	public WebDriver driver;
	public PatentSearchPage patentSearch;
	String publishedDate;
	String filedDate;
	String searchString;

	@BeforeClass
	public void launchingWebBrowser() throws IOException {
		driver = GetDriver.driver;
		patentSearch = new PatentSearchPage(driver);

	}

	@Test(priority = 0, description = "Searching for patent")
	public void searchPatent() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
		Actions action = new Actions(driver);

		// Enter search result//
		wait.until(ExpectedConditions.visibilityOf(patentSearch.searchArea()));
		action.moveToElement(patentSearch.searchArea()).sendKeys("p").build().perform();
	}

	@Test(priority = 1)
	public void acceptingTermsConditions() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
		Actions action = new Actions(driver);

		// Agreeing to terms and conditions //
		wait.until(ExpectedConditions.visibilityOf(patentSearch.agreeTermsandConditionsButton()));
		action.moveToElement(patentSearch.agreeTermsandConditionsButton()).perform();
		patentSearch.agreeTermsandConditionsButton().click();
	}

	@Test(priority = 2, invocationCount = 1)
	public void differenceBetweenDates() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
		Actions action = new Actions(driver);

		// If results are found based on the search input//
		List<WebElement> elements = driver
				.findElements(By.xpath("//tr[1]/td[3]/ul[@class='associations flex column']/li[1]"));
		if (elements.size() > 0) {

			// Select the first search result//
			wait.until(ExpectedConditions.visibilityOf(patentSearch.firstSearchResult()));
			action.moveToElement(patentSearch.firstSearchResult()).click().build().perform();

			// Check if there are any published date//
			List<WebElement> publishedDateElements = driver.findElements(By.xpath(
					"//li[1]/table[1]/tr[3]/td[1]/b[text()='Publication date']/ancestor::td/following-sibling::td"));

			if (publishedDateElements.size() > 0) {

				// Extracting first published date //
				wait.until(ExpectedConditions.visibilityOf(patentSearch.publishedDateInFirstTable()));
				action.moveToElement(patentSearch.publishedDateInFirstTable()).build().perform();
				String publishedDateContext = patentSearch.publishedDateInFirstTable().getText();
				publishedDate = publishedDateContext.substring(0, 10);

				// Check if there are any filed date//
				List<WebElement> filedDateElements = driver.findElements(By.xpath(
						"//li[1]/table[1]/tr[4]/td[1]/b[text()='Filing date']/ancestor::td/following-sibling::td"));

				if (filedDateElements.size() > 0) {

					// Extracting first filed date //
					wait.until(ExpectedConditions.visibilityOf(patentSearch.filedDateInFirstTable()));
					action.moveToElement(patentSearch.filedDateInFirstTable()).build().perform();
					String filedDateContext = patentSearch.filedDateInFirstTable().getText();
					filedDate = filedDateContext.substring(0, 10);

					// Finding difference between published and filed date//
					LocalDate startDate = LocalDate.parse(filedDate);
					LocalDate endDate = LocalDate.parse(publishedDate);

					Period difference = Period.between(startDate, endDate);

					// Printing patent title //

					System.out.println("Patent Title : " + patentSearch.patentTitle().getText());

					// Printing filed and published date //
					System.out.println("Filed Date = " + startDate + "\nPublished Date = " + endDate);

					// Print the difference//
					System.out.println(
							"-----------------Difference between filed and published date is---------------\n : "
									+ difference.getYears() + " years, " + difference.getMonths() + " months, "
									+ difference.getDays() + " days");

					long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

					int days = difference.getYears() * 365 + difference.getMonths() * 30 + difference.getDays();
					System.out.println("Difference in Days :" + daysBetween);
				}

				// If there are no published dare print no date available//
				else {
					System.out.println("No Filed Date Available");
				}

			}
			// If there are no published date print no date available//
			else {
				System.out.println("No Published Date Available");
			}

		}
		// Print no result found if the search result does not have any values //
		else {
			System.out.println(" No result found with these search filters.");
		}

	}

	@Test(priority = 3, description = "API call positive test")

	public void apiSearchCall() throws IOException {

		baseURI = LoadProperties.getInstance().getProperty("baseURI");

		String payload = "{ \"searchTerms\": \"p\" }";

		Response response = given().header("Content-Type", "application/json").body(payload) // Attach the payload
				.post("/api/search"); 

		// Check whether the response is 200 or not //
		Assert.assertEquals(response.getStatusCode(), 200, "POST request failed!");
	}

	@Test(priority = 4, description = "API Long string search")
	public void apiSearchCallforLongString() throws IOException {

		baseURI = LoadProperties.getInstance().getProperty("baseURI");

		
		
	       try {
	            // Load file from the classpath (resources folder)
	           InputStream inputStream = differenceInFiledAndPublishedDate.class.getClassLoader().getResourceAsStream("RandomString.txt");

	            if (inputStream == null) {
	                System.out.println("File not found in classpath.");
	            } else {
	                // Read the content of the file into a string
	                searchString = new String(inputStream.readAllBytes());
	               // System.out.println(content); // Print file content
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
		String payload ="{\"searchTerms\": \""+searchString+"\"}";

		Response response = given().header("Content-Type", "application/json").body(payload) // Attach the payload
				.post("/api/search"); 

		// Check whether the response is 200 or not //
		Assert.assertEquals(response.getStatusCode(), 200, "POST request failed!");

	}

	@Test(priority = 5, description = "Negative Test Case")

	public void pageMalfunction() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
		Actions action = new Actions(driver);

		 try {
	            // Load file from the classpath (resources folder)
	           InputStream inputStream = differenceInFiledAndPublishedDate.class.getClassLoader().getResourceAsStream("RandomString.txt");

	            if (inputStream == null) {
	                System.out.println("File not found in classpath.");
	            } else {
	                // Read the content of the file into a string
	                searchString = new String(inputStream.readAllBytes());
	               // System.out.println(content); // Print file content
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

		// System.out.println(searchString);

		patentSearch.searchArea().clear();
		try {
		    wait.until(ExpectedConditions.visibilityOf(patentSearch.searchArea()));
		    patentSearch.searchArea().sendKeys(searchString);
		} catch (TimeoutException e) {
		    System.out.println("Element not found: " + e.getMessage());
		  Assert.fail("Timeout occurred while trying to interact with search area.");
		}

		//Assert.assertEquals(patentSearch.patentTitle().getText(), "PAT-INFORMED");

	}
}