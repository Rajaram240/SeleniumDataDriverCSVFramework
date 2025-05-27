package pageObjectEvents;

import org.openqa.selenium.By;

import baseTest.BaseTest;

public class AddCustomerPage extends BaseTest {
	public static String valueIneed;
	By lnkAddNewCustomer=By.xpath("//a[text()=\"New Account\"]");
	By txtCustomerName=By.xpath("//*[@name=\"name\"]");
	By Gender= By.name("rad1");
	By txtdob= By.name("dob");
	By txtaddress=By.name("addr");
	By txtcity=By.name("city");
	By txtpin=By.name("pinno");
	By txtstate=By.name("state");
	By txttelephoneno=By.name("telephoneno");
	By txtemailid=By.name("emailid");
	By btnSubmit=By.name("sub");
	
	public void clickAddNewCustomer() {
		driver.findElement(lnkAddNewCustomer).click();
			
	}

	public void custName(String cname) {
		driver.findElement(txtCustomerName).sendKeys(cname);
		
	}

	public void custgender(String cgender) {
		driver.findElement(Gender).click();
	}

	public void custdob(String mm,String dd,String yy) {
		driver.findElement(txtdob).sendKeys(mm);
		driver.findElement(txtdob).sendKeys(dd);
		driver.findElement(txtdob).sendKeys(yy);
		
	}

	public void custaddress(String caddress) {
		driver.findElement(txtaddress).sendKeys(caddress);
	}

	public void custcity(String ccity) {
		driver.findElement(txtcity).sendKeys(ccity);
	}

	public void custstate(String cstate) {
		driver.findElement(txtstate).sendKeys(cstate);
	}

	public void custpinno(String cpinno) {
		driver.findElement(txtpin).sendKeys(String.valueOf(cpinno));
	}

	public void custtelephoneno(String ctelephoneno) {
		driver.findElement(txttelephoneno).sendKeys(ctelephoneno);
	}

	public void custemailid(String cemailid) {
		driver.findElement(txtemailid).sendKeys(cemailid);
	}

	public void custsubmit() {
		driver.findElement(btnSubmit).click();
	}
}
