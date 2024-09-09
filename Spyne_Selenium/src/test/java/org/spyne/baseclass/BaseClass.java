package org.spyne.baseclass;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.spyne.constants.Constants;
import org.testng.annotations.*;

public class BaseClass implements Constants {


    @BeforeSuite
    public void beforeSuite(){

    }

    @BeforeTest
    public void beforeTest(){

    }


    @Parameters({"browser"})
    @BeforeMethod
    public WebDriver getDriver(@Optional String browser){
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
    public void quitDriver(){
        if(driverList.get() != null)
            driverList.get().quit();
    }

}
