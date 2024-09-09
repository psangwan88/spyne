package org.spyne.libs;

import com.aventstack.extentreports.ExtentTest;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;

public class Assertion_Lib extends ExtentReport_Lib{

    public boolean screenCapturePass, screenCaptureFail;
    public Assertion_Lib(WebDriver driver, ExtentTest test, String testName, boolean screenCaptureFail, boolean screenCapturePass, String screenshotpath){
        super(driver,test,testName,screenshotpath);
        this.screenCaptureFail = screenCaptureFail;
        this.screenCapturePass = screenCapturePass;
    }

    public void assertEquals(Object actual, Object expected, String desc){
        String strM = "Expected :" + expected.toString() + "|Actual :"+actual.toString();
        String message = desc + "|Details:"+strM;
        try {

            Assert.assertEquals(actual.toString(), expected.toString(),message);
            logPass(message,screenCapturePass);
        }
        catch(AssertionError e){
            logFail(message,screenCaptureFail);
            System.out.println("*********ASSERTION FAILED : EXPECTED "+ expected.toString() + " Actual :" + actual.toString());
            Assert.assertEquals(actual.toString(), expected.toString(),message);
        }

    }


    public void assertTrue(boolean actual, String desc){
        String strM = "Expected :" + true + "|Actual :"+actual;
        String message = desc + "|Details:"+strM;
        try {
            Assert.assertTrue( actual,message);
            logPass(message,screenCapturePass);
        }
        catch(AssertionError e){
            logFail(message ,screenCaptureFail);
            System.out.println("*********ASSERTION FAILED : EXPECTED "+ true + " Actual :" + actual);
            Assert.assertTrue( actual,message);
        }

    }

    public void assertFalse(boolean actual, String desc){
        String strM = "Expected :" + false + "|Actual :"+actual;
        String message = desc + "|Details:"+strM;
        try {
            Assert.assertFalse(actual,message);
            logPass(message,screenCapturePass);
        }
        catch(AssertionError e){
            logFail(message,screenCaptureFail);
            System.out.println("*********ASSERTION FAILED : EXPECTED "+ false + " Actual :" + actual);
            Assert.assertFalse(actual,message);
        }

    }
}
