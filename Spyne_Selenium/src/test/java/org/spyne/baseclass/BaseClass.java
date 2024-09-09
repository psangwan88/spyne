package org.spyne.baseclass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.spyne.constants.Constants;
import org.spyne.libs.Generic_Lib;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class BaseClass implements Constants {
    public static int passCount = 0;
    public static int failCount = 0;
    public static ExtentReports extentReport;
    @BeforeSuite
    public void beforeSuite(){
        initializeReporter();
    }

    @BeforeTest
    public void beforeTest(){

    }


    @Parameters({"browser"})
    @BeforeMethod
    public WebDriver beforeMethod(ITestResult result, @Optional String browser){
        testName.set(result.getMethod().getMethodName());
        testList.set(extentReport.createTest(testName.get()));
        driverList.set(launchBrowser(browser));
        return driverList.get();
    }


    public WebDriver launchBrowser(String browser){
        WebDriver driver;
        if(browser == null)
            browser = "chrome"; // defualt value for a browser
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }
        return driver;
    }


    @AfterSuite
    public void afterSuite(){
        Generic_Lib.writeResult_CSV(csvResultFilePath, testStatus);
    }

    @AfterTest
    public void afterTest(){

    }


    @AfterMethod
    public  void endTest_setData(ITestResult result){
        setTestStatus(result);
        flushReport();
        if(driverList.get() != null)
            driverList.get().quit();


    }
    public synchronized void setTestStatus(ITestResult result){
        String res = result.getStatus() == 1 ? "Pass" : "Fail";
        if(res.equals("Pass")) {
            passCount++;
            testList.get().pass("Test has passed");
        }
        else {
            failCount--;  // not considering skips as of now in MVP
            testList.get().fail(result.getThrowable());
        }
        testStatus.add(new String[] {testName.get() + "," + res });
    }
    public synchronized void flushReport(){
        extentReport.flush();
    }


    public static void initializeReporter(){
        extentReport = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(extentReportPath);
        reporter.config().setReportName("SPYNE Selenium Report");
        reporter.config().setDocumentTitle("Automation Results");
        reporter.config().setTheme(Theme.DARK);
        extentReport.attachReporter(reporter);
        //reports.setSystemInfo("Created by", "Praveen Kumar");
        extentReport.setSystemInfo("URL", url);
        extentReport.setSystemInfo("OS", System.getProperty("os.name"));

    }
}
