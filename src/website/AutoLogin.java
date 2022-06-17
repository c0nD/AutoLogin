package website;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;


/*
 * @Author: c0nD
 * @Version: 1.0.0
 * 
 * Current ChromeDriver Version: 102.0.5005.61
 * Current Chrome Version (user's sake): 102.0.5005.115
 * 
 * Make sure to keep the chromedriver.exe up to date. To do this go to
 * chrome://settings/help in order to find your version of chrome, and use
 * https://chromedriver.chromium.org/downloads to get new versions of 
 * ChromeDriver. Keep in mind the last set of numbers don't matter that much
 * because Selenium will find the correct version dependent on the first numbers (ie 102).
 * 
 */
public class AutoLogin extends Thread{
	public static WebDriver driver = null;
	
	// Add to the arrays for different websites, the websiteIndex indicates which website
	// you'd like to launch after setting them all up
	public  static String websiteURL[] = new String[10];
	public static String usernameID[] = new String[10];
	public static String passwordID[] = new String[10];
	public final static int websiteIndex = 1;
	
	/*
	 * Main Method.
	 * 
	 * @throws Exception - BufferedReader
	 * @param args - default args
	 */
	public static void main(String args[]) throws Exception{
		// Setup ChromeDriver
		System.setProperty("webdriver.chrome.driver",".\\driver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Will poll for 10s to find element

		// Reading user/password and formatting to variables
		File file = new File(".\\src\\website\\credentials.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));	
		String user = br.readLine();
		String password = br.readLine();
		br.close();
		user = user.replaceFirst("user:", "");
		password = password.replaceFirst("password:", "");
		
		setup();
		
		// Website login, in this case I'm using it for CS50.
		driver.get(websiteURL[websiteIndex]);
		
		WebElement webUsername = driver.findElement(By.id(usernameID[websiteIndex])); 
		WebElement webPassword = driver.findElement(By.id(passwordID[websiteIndex]));
		// WebElement signIn = driver.findElement(By.id("sign-in")); // Implement later if hitting 'enter/return' doesn't login

		webUsername.sendKeys(user);
		webPassword.sendKeys(password);
		webPassword.sendKeys(Keys.RETURN);
		// Optional navigation
		buttonPress(null, "Resume Course");
	}
	
	
	/*
	 * If there is a button/link that needs to be pressed, you can use this
	 * to help navigate through the website to where you'd like to be. *note
	 * that 
	 * 
	 * @param continueButtonXpath - the html path of the element/button you'd like to press
	 * The format is as follows: "//tagname[@Attribute=’Value’]" , alternatively when using inspect
	 * element you can: Right Click Code -> Copy -> XPath
	 * @param continueButtonLinkTitle - The partial/full title of whatever link you'd like to press.
	 */
	public static void buttonPress(String continueButtonXpath, String continueButtonLinkTitle) {
		Actions action = new Actions(driver);
		WebElement element = null;
		if (continueButtonXpath != null) {
			element = driver.findElement(By.xpath(continueButtonXpath));
			action.moveToElement(element).perform();
			action.moveToElement(element).click();
			action.moveToElement(element).build();
		} else if (continueButtonLinkTitle != null) {
			element = driver.findElement(By.partialLinkText(continueButtonLinkTitle));
			element.click();
		}
	}
	
	public static void setup() {
		setWebsite("https://authn.edx.org/login", 1);
		setWebsiteUsernameID("emailOrUsername", 1);
		setWebsitePasswordID("password", 1);
	}
	
	public static void setWebsite(String url, int index) {
		websiteURL[index] = url;
	}
	
	public static void setWebsiteUsernameID(String usernameID, int index) {
		AutoLogin.usernameID[index] = usernameID;
	}
	
	public static void setWebsitePasswordID(String passwordID, int index) {
		AutoLogin.passwordID[index] = passwordID;
	}
}
	
