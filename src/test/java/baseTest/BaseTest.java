package baseTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.log4j.Logger;

import utils.ConfigLoader;
import utils.Constants;

import com.aventstack.extentreports.Status;

import java.time.Duration;

public class BaseTest {
	protected ExtentTest logger; 
	protected ExtentReports extent;
	ExtentTest test;
	public static WebDriver driver;
	public static Logger log = Logger.getLogger(BaseTest.class);
	ConfigLoader confg=new ConfigLoader();
	public String username=confg.getUsername();
	public String password=confg.getPassword();
	
	@BeforeTest
	public void beforeTestMethod() {
		//logger.info("Setting up ExtentReports...");
		ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");
		spark.config().setReportName("Automation Test Results");
		spark.config().setDocumentTitle("Test Report");
		spark.config().setTheme(Theme.DARK);
		// Attach reporter to ExtentReports
		extent = new ExtentReports();
		extent.attachReporter(spark);
		extent.setSystemInfo("Tester", "Rajaram");
		//logger.info("ExtentReports configured successfully.");
	}	
	@BeforeMethod
	@Parameters("browser")
	public void beforeMethod(String browser) throws InterruptedException
	{
		setupDriver(browser);
		System.out.println("Driver is being initialized...");
		driver.manage().window().maximize();
		driver.get(Constants.url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		Thread.sleep(20);	}
	
	public void setupDriver(String browser) {
		System.out.println("Setting up WebDriver...");
		logger = extent.createTest("Browser Test: " + browser);
		logger.info("Initializing browser: " + browser);
		switch (browser.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver(); // Selenium Manager handles driver
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			throw new IllegalArgumentException("Unsupported browser: " + browser);
		}
		logger.info(browser + " launched successfully.");
		logger.info("Launched browser: " + browser);
	}
	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			logger.fail(result.getThrowable()); // Logs the actual exception
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test Case Passed", ExtentColor.GREEN));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
			logger.skip(result.getThrowable());
		}
		driver.quit();
	}

	@AfterTest
	public void afterTest()
	{
		extent.flush();
		
	}
	@AfterClass
	public void teardown()
	{
		if (driver != null) {
	        driver.quit();
	    } else {
	        System.out.println("Driver was not initialized, skipping quit.");
	    }
	}
}

