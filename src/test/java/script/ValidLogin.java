package script;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import generic.BaseTest;
import page.EnterTimeTrackPage;
import page.LoginPage;

public class ValidLogin extends BaseTest {
	@Test(dataProvider = "getData", priority = 1, groups = { "smoke" })
	public void testValidLogin(String un, String pw, String eTitle) {
		LoginPage login = new LoginPage(driver);
		Reporter.log("Enter Valid User Name", true);
		login.enterUserName(un);
		test.info("UserName is enetred");
		Reporter.log("Enter Valid Password", true);
		login.enterPassword(pw);
		test.info("Password is enetred");
		Reporter.log("Click on Login Button", true);
		login.clickLogin();
		test.info("Login Button is clicked");
		Reporter.log("Verify Home page is opened", true);
		EnterTimeTrackPage ett = new EnterTimeTrackPage(driver);
		boolean result = ett.verifyPageTitle(wait, eTitle);
		Assert.assertTrue(result, "Home Page is not displayed");
		test.pass("Home Page is Displayed");
		test.info("HomePage is displayed");
		Reporter.log("Home Page is Displayed", true);

	}
}