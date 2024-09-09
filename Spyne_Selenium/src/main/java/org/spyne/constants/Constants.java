package org.spyne.constants;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;

public interface Constants {

    public static ThreadLocal<WebDriver> driverList = new ThreadLocal<>();
    public static String url = "https://www.spyne.ai/image-upscaler";
    public static String projectPath = System.getProperty("user.dir");
    public static String resourcePath = projectPath + "/src/test/resources/";
    public static String extentReportPath = projectPath + "/Reports/";
    ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(extentReportPath  + "extentReport.html");
    ExtentReports extentReport = new ExtentReports();
    public static ExtentReports report = new ExtentReports();

}
