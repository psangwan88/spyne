package org.spyne.libs;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReport_Lib {

    WebDriver driver = null;
    ExtentTest test;
    String testName;
    String screenShotPath ;


    public ExtentReport_Lib(WebDriver driver, ExtentTest test, String testName, String screenshotPath){
        this.driver = driver;
        this.testName = testName;
        this.test = test;
        this.screenShotPath = screenshotPath;
    }

    public void markTestFail(){
        test.fatal("Check earlier errors/warnings");
    }

    public void logPass(String desc, boolean imageCapture){
        String strpath = "";
        if(imageCapture) {
            String imgName = "screenshot/"  + testName + getTimestamp() +".png";
            String strImageName = screenShotPath + "/"+imgName;
            strpath = takeScreenShot(strImageName);
            try {
                test.pass(desc + "\nSee Image  :" ,MediaEntityBuilder.createScreenCaptureFromPath(imgName).build());
            }
            catch(Exception e){}
        }
        else
            test.pass(desc);
    }


    public void logFail(String desc, boolean imageCapture){
        String strpath = "";
        if(imageCapture) {
            String imgName = "screenshot/"  + testName + getTimestamp() +".png";
            String strImageName = screenShotPath + "/"+imgName;
            strpath = takeScreenShot(strImageName);
            try {
                test.fail(desc + "\nSee Image  :", MediaEntityBuilder.createScreenCaptureFromPath(imgName).build());
            }
            catch (Exception e) {}

        }
        else
            test.fail(desc);
    }

    public void logSkipped(String desc){
        test.skip("Test skipped -> " + desc);
    }
    
    public void logWarning(String desc){
        test.log(Status.WARNING, desc);
    }
    public void logInfo(String desc){
        test.log(Status.INFO,desc);
    }

    public String takeScreenShot(String strPath){
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) this.driver);
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
    // get hours and mins and secs only, not reusing datelib as it makes this file depend on others
    public static String getTimestamp(){
        String currentTime = new SimpleDateFormat("hh:mm:ss").format(new Date());
        currentTime = currentTime.replace(" ", "_t_").replace("-", "_").replace(":","_");
        return currentTime;
    }




}
