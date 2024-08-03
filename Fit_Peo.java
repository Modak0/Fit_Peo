package Fit_Peo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
public class Fit_Peo {
	@Test
	public void test() {
		System.setProperty("webdriver.chrome.driver", "./softwares/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions act = new Actions(driver);
		SoftAssert s = new SoftAssert();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// 1. Navigating to the FitPeo Home page
		driver.get("https://fitpeo.com/");
		// 2. Navigating to the Revenue Calculator Page:
		driver.findElement(By.xpath("//div[text()='Revenue Calculator']")).click();
		wait.until(ExpectedConditions
				.visibilityOf(driver.findElement(By.xpath("//span[text()='Patients should be between 0 to 2000']"))));

		// 3. Scroll Down to the Slider section:
		js.executeScript("window.scrollTo(0,300)");
		WebElement drag_button = driver.findElement(By.xpath("//input[@aria-orientation='horizontal']"));
		// 4. Adjusting the Slider:
		act.dragAndDropBy(drag_button, 94, 0).build().perform();

		WebElement text_filed = driver.findElement(By.xpath("//input[@type='number']"));
		text_filed.sendKeys(Keys.CONTROL + "a", Keys.DELETE);

		// 5. Updating the Text Field:
		text_filed.sendKeys("560");
		// 6. Validating Slider Value:
		Assert.assertEquals(text_filed.getAttribute("value"), "560");
		// 7. Selecting CPT Codes:
		js.executeScript("window.scrollTo(0,300)");

		List<WebElement> check_box = driver
				.findElements(By.xpath("//div[@class='MuiBox-root css-4o8pys']/label/span[1]"));
		check_box.get(0).click();
		check_box.get(1).click();
		check_box.get(2).click();
		check_box.get(7).click();
		js.executeScript("window.scrollTo(0,-100)");
		String actual_Total_Gross_evenue_Per_Year = driver
				.findElement(By.xpath("//h3[@class='MuiTypography-root MuiTypography-h3 crimsonPro css-k7aeh2']"))
				.getText();

		System.out.println(actual_Total_Gross_evenue_Per_Year);

		String replaed = actual_Total_Gross_evenue_Per_Year.replace("$", "");
		System.out.println(replaed);
		double actualvalue = Double.parseDouble(replaed);
		double expected_Total_Gross_evenue_Per_Year = 110700.00;
		// 8. Validating Total Recurring Reimbursement
		s.assertEquals(actualvalue, expected_Total_Gross_evenue_Per_Year,
				"Total Recurring Reimbursement for all Patients Per Month is Not valid");
		

		driver.quit();
        s.assertAll();
	}
}

