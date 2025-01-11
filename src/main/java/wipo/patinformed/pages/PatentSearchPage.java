package wipo.patinformed.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PatentSearchPage {
	WebDriver driver;

	public PatentSearchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	// Search button//
	@FindBy(xpath = "//input[contains(@placeholder,'Search pharmaceutical patents by INN - Ex:')]")
	WebElement searchArea;
	
	//Terms and Condition agree button//
	@FindBy(xpath="//button[text()='I have read and agree to the terms']")
	WebElement agreeTermsandConditionsButton;
	
	//First search result//
	@FindBy(xpath="//tr[1]/td[3]/ul[@class='associations flex column']/li[1]")
	WebElement firstSearchResult;
	
	//First Published Date in first table //
	@FindBy(xpath="//li[1]/table[1]/tr[3]/td[1]/b[text()='Publication date']/ancestor::td/following-sibling::td")
	WebElement publishedDateInFirstTable;
	
	//First Filed date of first search result //
	@FindBy(xpath="//li[1]/table[1]/tr[4]/td[1]/b[text()='Filing date']/ancestor::td/following-sibling::td")
	WebElement filedDateInFirstTable;
	
	//Title of the Patent //
	@FindBy(xpath="//h3[@class='title']")
	WebElement patentTitle;
	
	@FindBy(xpath="//span[contains(text(),'No result found with these search filters.')]")
	WebElement noResultFound;
	
	@FindBy(xpath="//h1[text()='PAT-INFORMED']")
	WebElement pageHeader;
	
	

	public WebElement searchArea() {
		return searchArea;
	}
	
	public WebElement agreeTermsandConditionsButton() {
		return agreeTermsandConditionsButton;
	}
	
	public WebElement firstSearchResult() {
		return firstSearchResult;
	}
	
	public WebElement publishedDateInFirstTable() {
		return publishedDateInFirstTable;
	}
	
	public WebElement filedDateInFirstTable() {
		return filedDateInFirstTable;
	}
	
	public WebElement patentTitle() {
		return patentTitle;
	}
	
	public WebElement pageHeader() {
		return pageHeader;
	}
	
	
}