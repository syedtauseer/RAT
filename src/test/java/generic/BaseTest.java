package generic;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.beust.jcommander.Parameter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest implements IAutoConst {
	public WebDriver driver;
	public WebDriverWait wait;
	public static ExtentReports report;
	public static ExtentTest test;

	@BeforeSuite
	public void generateReport() {
		report = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter(REPORT_PATH);
		report.attachReporter(spark);
	}

	@AfterSuite
	public void publishReport() {
		report.flush();
	}

	@BeforeMethod
	public void createTest(Method testMethod) {
		String testname = testMethod.getName();
		test = report.createTest(testname);
	}

	@Parameters({ "config" })
	@BeforeMethod
	public void openApp(@Optional(CONFIG_PATH) String config) {
		String browser = utility.getProperty(config, "BROWSER");
		String grid = utility.getProperty(config, "GRID");
		if (grid.equalsIgnoreCase("Yes")) {
			String remote = utility.getProperty(config, "REMOTE_URL");
			try {
				URL url = new URL(remote);
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setBrowserName(browser);
				driver = new RemoteWebDriver(url, cap);
			} catch (Exception e) {
				e.getMessage();
			}
		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		driver.manage().window().maximize();
		String sITO = utility.getProperty(config, "ITO");
		long ITO = Long.parseLong(sITO);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ITO));
		String sETO = utility.getProperty(config, "ETO");
		long ETO = Long.parseLong(sETO);
		wait = new WebDriverWait(driver, Duration.ofSeconds(ETO));
		String App_URL = utility.getProperty(config, "APP_URL");
		driver.get(App_URL);
	}

	@AfterMethod
	public void closeApp(ITestResult result, Method testMethod) {
		int status = result.getStatus();
		String testName = testMethod.getName();
		if (status == 2) {
			TakesScreenshot t = (TakesScreenshot) driver;
			File srcPath = t.getScreenshotAs(OutputType.FILE);
			File dstPath = new File(IMG_PATH + testName + IMG_EXTENSION);
			try {
				FileUtils.copyFile(srcPath, dstPath);
				test.addScreenCaptureFromPath("./../img/" + testName + IMG_EXTENSION);
				test.fail(
						MediaEntityBuilder.createScreenCaptureFromPath("./../img/" + testName + IMG_EXTENSION).build());
				String msg = result.getThrowable().getMessage();
				test.fail("TestScript Failure Reason : " + msg);
			} catch (Exception e) {

			}
		}
		driver.close();
	}

	@DataProvider
	public Iterator<String[]> getData(Method testMethod) {
		String sheetName = testMethod.getName();
		Iterator<String[]> data = utility.getDataFromExcel(DATA_PATH, sheetName);
		return data;
	}
}
