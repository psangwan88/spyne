package org.spyne.baseclass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.spyne.constants.Constants;
import org.spyne.libs.Assertion_Lib;
import org.spyne.constants.Generic_Lib;
import org.spyne.libs.Web_Lib;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;


public class BaseClass implements Constants {
    public static ThreadLocal<WebDriver> driverList = new ThreadLocal<>();
    public static ThreadLocal<ExtentTest> testList = new ThreadLocal<>();
    public static ThreadLocal<Assertion_Lib> assertion = new ThreadLocal<>();
    public static ThreadLocal<Web_Lib> web_lib = new ThreadLocal<>();
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
        assertion.set(new Assertion_Lib(driverList.get(), testList.get(),testName.get(), screenshotOnFailure, screenshotOnPass,screenShotPath));
        web_lib.set(new Web_Lib(driverList.get()));
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
        }
        else {
            failCount++;  // not considering skips as of now in MVP
            testList.get().fail(result.getThrowable());
            if(screenshotOnFailure) {
                String imagePath = screenShotPath + "/screenshot/"  + testName.get() + ".png";
                takeScreenShot(imagePath);
                try {
                    testList.get().fail("Test Failed" + "\nSee Image  :", MediaEntityBuilder.createScreenCaptureFromPath("screenshot/"  + testName.get() + ".png").build());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
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

    public String takeScreenShot(String strPath){
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) driverList.get());
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile = new File(strPath);
            FileUtils.copyFile(SrcFile, DestFile);
            return strPath;
        }
        catch (Exception e){
            System.out.println("Due to some error not able to take screenshot" );
            e.printStackTrace();
            throw new RuntimeException("Exception during capturing screenshot");

        }
    }


}
