This project is built using a Java-based Selenium Testing Framework following the Page Object Model (POM) design pattern. It validates login functionality for a sample web application using both:

✅ Standard credential-based testing

✅ Data-driven testing (DDT) via CSV file integration

📦 Key Features
🧪 Login Validation: Automated test scenarios for login functionality.

📊 Data-Driven Testing (DDT):

Input credentials are read from a CSV file.

Results (Pass/Fail) are written back to the CSV file.

🔧 Utility Classes:

CSVUtils.java: Handles reading/writing CSV files.

Constants.java: Centralized file paths and column names for reusability.

ConfigLoader.java: Loads locators and other configurations from config.properties.

📸 UI Test Screenshots:

Automatically captures screenshots for failed test cases.

📈 Extent Reports:

Rich HTML reports with test steps, logs, and screenshots.

📝 Log4j Logging:

Captures detailed execution logs for debugging and auditing.

🔁 Retry Mechanism:

RetryAnalyzer class re-runs failed test cases automatically to improve test stability.

🧰 Tools & Technologies
Java

Selenium WebDriver

TestNG

Apache Commons CSV / Custom CSVUtils

ExtentReports

Log4j

Page Object Model (POM) architecture

📁 Project Structure (Highlights)
bash
Copy
Edit
src/
├── main/
│   ├── java/
│   │   ├── pageObjects/          # Page classes (POM)
│   │   ├── utils/                # CSVUtils, ConfigLoader, Constants
│   │   └── base/                 # BaseTest setup
│
├── test/
│   ├── java/
│   │   └── testCases/            # LoginDDT, RetryAnalyzer
│
├── resources/
│   ├── config.properties         # Locators and settings
│   ├── Login.csv                 # Test data
│   └── extent-config.xml         # Reporting layout (optional)

✅ How to Run
Clone the repo.

Update config.properties and CSV file paths if needed.

Run the test suite using TestNG or your preferred IDE.

Check the reports folder for ExtentReports and logs/ for execution logs.

