package utils;

import java.io.File;

public interface Constants {
String url="https://demo.guru99.com/V1/index.php";
public static final String CSV_PATH = System.getProperty("user.dir") + File.separator + "src" +
        File.separator + "test" + File.separator + "resources" +
        File.separator + "LoginData.csv"; // New file name
public static final String LOGIN_EXPECTED_RESULT_COL = "ExpectedResult"; // If you have one
public static final String LOGIN_STATUS_COL = "Status";
public static final String LOGIN_USERNAME_COL = "Username"; // Column names from CSV header
public static final String LOGIN_PASSWORD_COL = "Password";


}
