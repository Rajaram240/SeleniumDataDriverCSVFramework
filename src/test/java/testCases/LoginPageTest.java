package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import baseTest.BaseTest;
import utils.ConfigLoader;
import pageObjectEvents.LoginPage;

public class LoginPageTest extends BaseTest{
	LoginPage loginpage=new LoginPage();
	
	@Test
	public void runLoginTest() throws InterruptedException
	{	
		Thread.sleep(2000);
	loginpage.setUserName(username);
	log.info("Entered username");
	loginpage.setPassword(password);
	log.info("Entered password");
	loginpage.clickSubmit();
	if(driver.getTitle().equals("GTPL Bank Manager HomePage"))
	{
		Assert.assertTrue(true);
		log.info("Login test passed");
	}
	else
	{
		Assert.assertTrue(false);
		
		log.info("Login test failed");
	
	}
	Thread.sleep(20);
	loginpage.clickLogout();
	driver.switchTo().alert().accept();
	if(driver.getTitle().equals("GTPL Bank Home Page"))
	{
		Assert.assertTrue(true);
		log.info("Login test passed");
	}
	else
	{
		Assert.assertTrue(false);
		log.info("Login test failed");
	}
	  log.info("Logged Out successfully, Back to login page..");
	}
}