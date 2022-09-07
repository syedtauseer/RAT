package script;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import generic.BaseTest;
import page.LoginPage;

public class InValidLogin extends BaseTest {
	@Test(dataProvider = "getData", priority = 2, groups = { "Smoke" })
	public void testInValidLogin(String un, String pw) {
		LoginPage login = new LoginPage(driver);
		Reporter.log("Enter Valid User Name", true);
		login.enterUserName(un);
		test.info("User Name is enetered");
		Reporter.log("Enter Valid Password", true);
		login.enterPassword(pw);
		test.info("Password is enetered");
		Reporter.log("Click on Login Button", true);
		login.clickLogin();
		test.info("Cliked on login button");
		Reporter.log("Verify Error Message is displayed", true);
		boolean result = login.verifyErrorMsg(wait);
		Assert.assertTrue(result, "error message is not displayed");
		test.pass("error message is Displayed");
		test.info("error message is Displayed");
		Reporter.log("Error Message is Displayed", true);

	}

}
