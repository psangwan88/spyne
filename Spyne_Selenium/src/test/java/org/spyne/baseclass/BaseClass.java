package org.spyne.baseclass;

import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.spyne.constants.Constants;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class BaseClass implements Constants {

    static {
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Extent Report Sample");
        htmlReporter.config().setTheme(Theme.STANDARD);
        extentReport.attachReporter(htmlReporter);
    }
    @BeforeSuite
    public void beforeSuite(){

    }

    @BeforeTest
    public void beforeTest(){

    }


    @Parameters({"browser"})
    @BeforeMethod
    public WebDriver beforeMethod(ITestResult result, @Optional String browser){
        extentReport.createTest(result.getMethod().getMethodName());
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

    }

    @AfterTest
    public void afterTest(){

    }


    @AfterMethod
    public  void quitDriver(){
        if(driverList.get() != null)
            driverList.get().quit();
        flushReport();
    }

    public synchronized void flushReport(){
        extentReport.flush();
    }
}
