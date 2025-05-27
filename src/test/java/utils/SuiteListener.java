package utils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IAnnotationTransformer;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;
import org.apache.commons.io.FileUtils;

import baseTest.BaseTest;

public class SuiteListener implements ITestListener,IAnnotationTransformer{
	public void onTestFailure(ITestResult result)
	{
		String filename=System.getProperty("user.dir")+File.separator+"screenshots"+File.separator+result.getMethod().getMethodName();
		File f1=((TakesScreenshot)BaseTest.driver).getScreenshotAs(OutputType.FILE);
		
		try {
			FileUtils.copyFile(f1,new File(filename+".png"));
		} catch (IOException e) {
			  System.err.println("❌ Failed to save screenshot: " + e.getMessage());
		}
		}
	public void transform(
			ITestAnnotation annotation,Class testClass, Constructor testconstructor,Method testMethod)
	{
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}
}
