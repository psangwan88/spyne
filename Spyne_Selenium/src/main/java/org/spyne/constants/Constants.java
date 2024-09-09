package org.spyne.constants;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Constants {

    public static String projectPath = System.getProperty("user.dir");
    public static String resourcePath = projectPath + "/src/test/resources/";
    public static Map<String,String> properties = Generic_Lib.readConfigFile(resourcePath+"config.properties");
    public static String extentReportPath = projectPath + "/Reports/TestResults.html";
    public static String screenShotPath = projectPath + "/Reports";
    public static String csvResultFilePath = projectPath + "/result.csv";
    public static List<String[]> testStatus = new ArrayList<>();
    public static ThreadLocal<String> testName = new ThreadLocal<>();
    public static String url = properties.get("url");
    public static boolean screenshotOnFailure =  properties.get("screenshot_failure").equals("true") ? true : false;
    public static boolean screenshotOnPass =  properties.get("screenshot_pass").equals("true") ? true : false;

    public static String defaultBrowser = properties.get("browser");
    public static int implicitWait = Integer.parseInt(properties.get("implicit_timeout"));
    public static int explicitWait = Integer.parseInt(properties.get("explicit_timeout"));

}
