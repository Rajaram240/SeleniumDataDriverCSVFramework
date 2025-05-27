package pageObjectEvents;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import baseTest.BaseTest;

public class LoginPage extends BaseTest {

	By usernameInput = By.name("uid");
    By passwordInput = By.name("password");
    By loginButton   = By.name("btnLogin");
    By lnkLogout	=By.xpath("//*[text()=\"Log out\"]");

	
	public void setUserName(String uname)
	{
		driver.findElement(usernameInput).sendKeys(uname);
	}
	
	public void setPassword(String pwd)
	{
		driver.findElement(passwordInput).sendKeys(pwd);
	}
	
	
	public void clickSubmit()
	{
		driver.findElement(loginButton).click();
	}	
	
	public void clickLogout()
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)",driver.findElement(lnkLogout) );
		js.executeScript("arguments[0].click();", driver.findElement(lnkLogout));
		
	}
	
	
}