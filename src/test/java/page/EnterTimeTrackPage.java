package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EnterTimeTrackPage {
	public EnterTimeTrackPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public boolean verifyPageTitle(WebDriverWait wait, String eTtile) {
		try {
			wait.until(ExpectedConditions.titleContains(eTtile));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
