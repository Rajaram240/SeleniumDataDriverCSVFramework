package testCases;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import baseTest.BaseTest;
import pageObjectEvents.LoginPage;
import utils.Constants;
import utils.CSVUtils; 

public class LoginWithDDT extends BaseTest {

    @BeforeSuite
    public void setupSuite() {
        try {
            CSVUtils.loadCSV(Constants.CSV_PATH);
            log.info("CSV file loaded for data operations: " + Constants.CSV_PATH);
        } catch (IOException e) {
            log.fatal("FATAL: Failed to load CSV file: " + Constants.CSV_PATH, e);
            throw new RuntimeException("Test suite aborted: Could not load CSV test data.", e);
        }
    }

    @DataProvider(name = "LoginData")
    public Object[][] getData() {
        int rowCount = CSVUtils.getRowCount();
        List<String> headers = CSVUtils.getHeaders();
        // Adjust for the number of columns you want to pass to the @Test method
        // If your test method takes user, pwd, expectedResult, and you want to pass row index for writing
        // Example: user, pwd, excelRowIndex (3 parameters)
        // If your test method takes user, pwd, expectedResult (3 parameters), no row index if using column names
        // Let's pass username, password, expectedResult, and the 0-based data row index
        
        // Find column indices based on headers for easy mapping to @Test method parameters
        int usernameColIndex = headers.indexOf(Constants.LOGIN_USERNAME_COL);
        int passwordColIndex = headers.indexOf(Constants.LOGIN_PASSWORD_COL);
        int expectedResultColIndex = headers.indexOf(Constants.LOGIN_EXPECTED_RESULT_COL);
        
        // Validate column existence
        if (usernameColIndex == -1 || passwordColIndex == -1 || expectedResultColIndex == -1) {
            throw new RuntimeException("Required CSV columns (Username, Password, ExpectedResult) not found.");
        }

        Object[][] loginData = new Object[rowCount][4]; // username, password, expectedResult, dataRowIndex (0-based)

        for (int i = 0; i < rowCount; i++) { // Loop from 0 to rowCount-1 (0-based data rows)
            loginData[i][0] = CSVUtils.getCellData(i, Constants.LOGIN_USERNAME_COL);
            loginData[i][1] = CSVUtils.getCellData(i, Constants.LOGIN_PASSWORD_COL);
            loginData[i][2] = CSVUtils.getCellData(i, Constants.LOGIN_EXPECTED_RESULT_COL);
            loginData[i][3] = i; // Store the 0-based data row index for writing results
        }
        return loginData;
    }

    @Test(dataProvider = "LoginData")
    public void loginDDT(String user, String pwd, String expectedResult, int dataRowIndex) {
        // 'driver' is initialized by BaseTest's @BeforeMethod

        LoginPage lp = new LoginPage();
        lp.setUserName(user);
        log.info("Username provided: " + user);
        lp.setPassword(pwd);
        log.info("Password provided.");
        lp.clickSubmit();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String testResult = "FAIL"; // Default status

        try {
            if (isAlertPresent(wait)) {
                log.warn("Login failed for user: " + user + " - Alert detected.");
                driver.switchTo().alert().accept();
                driver.switchTo().defaultContent();
                testResult = "FAIL"; // Explicitly set to FAIL if an alert means failure
            } else {
                // For simplicity, let's assume no alert means initial success
                log.info("Login passed for user: " + user);
                lp.clickLogout();
                if (isAlertPresent(wait)) {
                    driver.switchTo().alert().accept();
                    driver.switchTo().defaultContent();
                    testResult = "PASS";
                } else {
                    log.error("Logout alert not found for user: " + user + " after logout click.");
                    testResult = "FAIL";
                }
            }
        } catch (Exception e) {
            log.error("An unexpected error occurred during login for user: " + user + " - " + e.getMessage(), e);
            testResult = "FAIL";
            try {
                if (isAlertPresent(wait)) {
                    driver.switchTo().alert().accept();
                    driver.switchTo().defaultContent();
                }
            } catch (Exception alertHandleEx) {
                log.warn("Failed to handle alert during exception: " + alertHandleEx.getMessage());
            }
        } finally {
            // Write the result back to the 'Status' column for the current dataRowIndex (0-based)
            CSVUtils.setCellData(dataRowIndex, Constants.LOGIN_STATUS_COL, testResult);
            log.info("Result '" + testResult + "' written for data row index: " + dataRowIndex + " in CSV memory.");
            // driver.quit() is handled by BaseTest's @AfterMethod
        }
    }

    public boolean isAlertPresent(WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (org.openqa.selenium.TimeoutException | NoAlertPresentException e) {
            return false;
        }
    }

    @AfterSuite
    public void tearDownSuite() {
        try {
            CSVUtils.writeCSV(); // Save all changes from memory to the CSV file
            log.info("CSV file operations completed and file closed.");
        } catch (IOException e) {
            log.error("Error writing/closing CSV file: " + Constants.CSV_PATH, e);
        }
    }
}